package com.health.service.mealservice.service.impl;

import static com.health.domain.exception.ErrorCode.CONSUME_FOOD_AND_MEAL_UN_MATCH;
import static com.health.domain.exception.ErrorCode.CONSUME_FOOD_NOT_FOUND;
import static com.health.domain.exception.ErrorCode.DAILY_MEAL_AND_DT_UN_MATCH;
import static com.health.domain.exception.ErrorCode.FOOD_NOT_FOUND;
import static com.health.domain.exception.ErrorCode.MEAL_NOT_FOUND;
import static com.health.domain.exception.ErrorCode.MEAL_USER_INVALID;

import com.health.domain.entity.ConsumeFoodEntity;
import com.health.domain.entity.DailyMealEntity;
import com.health.domain.entity.FoodEntity;
import com.health.domain.entity.MealEntity;
import com.health.domain.entity.UserEntity;
import com.health.domain.exception.CustomException;
import com.health.domain.repository.ConsumeFoodRepository;
import com.health.domain.repository.FoodRepository;
import com.health.domain.repository.MealRepository;
import com.health.service.mealservice.dto.ConsumeFoodServiceDto;
import com.health.service.mealservice.form.FoodServiceForm;
import com.health.service.mealservice.service.ConsumeFoodService;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ConsumeFoodServiceImpl implements ConsumeFoodService {

  private final MealRepository mealRepository;
  private final FoodRepository foodRepository;
  private final ConsumeFoodRepository consumeFoodRepository;

  @Override
  // nutrient 정보 업데이트 위해 기존 캐시 삭제
  @CacheEvict(cacheNames = "user", key = "@redisKeyComponent.intakeKey(#authId, #dailyMealDt)")
  public ConsumeFoodServiceDto addFoodToMeal
      (String authId, LocalDate dailyMealDt, Long mealId, FoodServiceForm foodServiceForm) {

    MealEntity findMeal = findMealById(mealId);
    DailyMealEntity findDailyMeal = findMeal.getDailyMeal();
    UserEntity findUser = findDailyMeal.getUser();

    validateMealOwner(authId, findUser);
    validateDailyMealDt(dailyMealDt, findDailyMeal);

    FoodEntity findFood = findFoodByCode(foodServiceForm.getFoodCode());

    ConsumeFoodEntity createdConsumeFood =
        ConsumeFoodEntity.create(findMeal, findFood, foodServiceForm.getConsumeAmount());

    findMeal.getConsumeFoodList().add(createdConsumeFood);

    // nutrient 업데이트
    findMeal.addNutrient(createdConsumeFood);
    findDailyMeal.addNutrient(createdConsumeFood);


    return ConsumeFoodServiceDto.fromEntity(createdConsumeFood);
  }

  @Override
  // nutrient 정보 업데이트 위해 기존 캐시 삭제
  @CacheEvict(cacheNames = "user", key = "@redisKeyComponent.intakeKey(#authId, #dailyMealDt)")
  public Long deleteConsumeFood
      (String authId, LocalDate dailyMealDt, Long mealId, Long consumeFoodId) {

    MealEntity findMeal = findMealById(mealId);
    DailyMealEntity findDailyMeal = findMeal.getDailyMeal();
    UserEntity findUser = findDailyMeal.getUser();

    validateMealOwner(authId, findUser);
    validateDailyMealDt(dailyMealDt, findDailyMeal);

    ConsumeFoodEntity findConsumeFood = getFindConsumeFoodById(consumeFoodId);

    validConsumeFoodMeal(findConsumeFood, findMeal);

    // nutrient 업데이트
    findMeal.minusNutrient(findConsumeFood);
    findDailyMeal.minusNutrient(findConsumeFood);

    findMeal.getConsumeFoodList().remove(findConsumeFood);

    consumeFoodRepository.delete(findConsumeFood);

    return findConsumeFood.getId();
  }

  private MealEntity findMealById(Long mealId) {
    return mealRepository.findById(mealId)
        .orElseThrow(() -> new CustomException(MEAL_NOT_FOUND));
  }

  private FoodEntity findFoodByCode(String code) {
    return foodRepository.findById(code)
        .orElseThrow(() -> new CustomException(FOOD_NOT_FOUND));
  }

  private ConsumeFoodEntity getFindConsumeFoodById(Long consumeFoodId) {
    return consumeFoodRepository.findById(consumeFoodId)
        .orElseThrow(() -> new CustomException(CONSUME_FOOD_NOT_FOUND));
  }

  private void validateMealOwner(String authId, UserEntity findUser) {
    if (!authId.equals(findUser.getAuthId())) {
      throw new CustomException(MEAL_USER_INVALID);
    }
  }

  private void validateDailyMealDt(LocalDate dailyMealDt, DailyMealEntity findDailyMeal) {
    if (dailyMealDt != findDailyMeal.getDailyMealDt()) {
      throw new CustomException(DAILY_MEAL_AND_DT_UN_MATCH);
    }
  }

  private void validConsumeFoodMeal(ConsumeFoodEntity findConsumeFood, MealEntity findMeal) {
    if(findConsumeFood.getMeal() != findMeal) {
      throw new CustomException(CONSUME_FOOD_AND_MEAL_UN_MATCH);
    }
  }

}
