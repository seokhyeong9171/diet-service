package com.health.service.mealservice.service.impl;

import static com.health.domain.exception.ErrorCode.DAILY_MEAL_NOT_FOUND;
import static com.health.domain.exception.ErrorCode.MEAL_AND_DAILY_MEAL_NOT_MATCH;
import static com.health.domain.exception.ErrorCode.MEAL_NOT_FOUND;
import static com.health.domain.exception.ErrorCode.USER_NOT_FOUND;

import com.health.domain.entity.DailyMealEntity;
import com.health.domain.entity.MealEntity;
import com.health.domain.entity.UserEntity;
import com.health.domain.exception.CustomException;
import com.health.domain.repository.ConsumeFoodRepository;
import com.health.domain.repository.DailyMealRepository;
import com.health.domain.repository.MealRepository;
import com.health.domain.repository.UserRepository;
import com.health.service.mealservice.dto.MealServiceDto;
import com.health.service.mealservice.form.MealServiceForm;
import com.health.service.mealservice.service.MealService;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MealServiceImpl implements MealService {

  private final UserRepository userRepository;
  private final MealRepository mealRepository;
  private final ConsumeFoodRepository consumeFoodRepository;
  private final DailyMealRepository dailyMealRepository;


  @Override
  @Transactional(readOnly = true)
  public List<MealServiceDto> getMealList(String authId, LocalDate dailyMealDt) {
    UserEntity findUser = findUserByAuthId(authId);
    DailyMealEntity findDailyMeal = findDailyMealByUserAndDt(findUser, dailyMealDt);

    return findDailyMeal.getMeals().stream()
        .map(MealServiceDto::fromEntity).toList();
  }

  @Override
  @Transactional(readOnly = true)
  public MealServiceDto getMealInfo(String authId, LocalDate dailyMealDt, Long mealId) {

    UserEntity findUser = findUserByAuthId(authId);
    DailyMealEntity findDailyMeal = findDailyMealByUserAndDt(findUser, dailyMealDt);

    MealEntity findMeal = findMealById(mealId);

    validateProperMeal(findMeal, findDailyMeal);

    return MealServiceDto.fromEntity(findMeal);
  }

  @Override
  public MealServiceDto createMeal(String authId, LocalDate dailyMealDt, MealServiceForm serviceForm) {

    UserEntity findUser = findUserByAuthId(authId);

    DailyMealEntity dailyMealEntity = findDailyMealByUserAndDt(findUser, dailyMealDt);

    MealEntity mealEntity =
        MealEntity.createNew(dailyMealEntity, dailyMealDt, serviceForm.toDomainForm());
    MealEntity savedMealEntity = mealRepository.save(mealEntity);

    dailyMealEntity.getMeals().add(savedMealEntity);

    return MealServiceDto.fromEntity(mealEntity);
  }


  @Override
  // nutrient 정보 업데이트 위해 기존 캐시 삭제
  @CacheEvict(cacheNames = "user", key = "@redisKeyComponent.intakeKey(#authId, #dailyMealDt)")
  public Long deleteMeal(String authId, LocalDate dailyMealDt, Long mealId) {
    UserEntity findUser = findUserByAuthId(authId);

    DailyMealEntity findDailyMeal = findDailyMealByUserAndDt(findUser, dailyMealDt);

    MealEntity findMeal = findMealById(mealId);

    validateProperMeal(findMeal, findDailyMeal);

    consumeFoodRepository.deleteByMeals(List.of(findMeal));

    mealRepository.delete(findMeal);


    return findMeal.getId();
  }

  private UserEntity findUserByAuthId(String authId) {
    return userRepository.findByAuthId(authId)
        .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
  }

  private DailyMealEntity findDailyMealByUserAndDt(UserEntity findUser, LocalDate dailyMealDt) {
    return dailyMealRepository.findByUserAndDailyMealDt(findUser, dailyMealDt)
        .orElseThrow(() -> new CustomException(DAILY_MEAL_NOT_FOUND));
  }

  private MealEntity findMealById(Long mealId) {
    return mealRepository.findById(mealId)
        .orElseThrow(() -> new CustomException(MEAL_NOT_FOUND));
  }

  private void validateProperMeal(MealEntity findMeal, DailyMealEntity findDailyMeal) {
    if (findMeal.getDailyMeal() != findDailyMeal) {
      throw new CustomException(MEAL_AND_DAILY_MEAL_NOT_MATCH);
    }
  }
}
