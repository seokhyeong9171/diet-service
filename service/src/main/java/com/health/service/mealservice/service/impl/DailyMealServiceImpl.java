package com.health.service.mealservice.service.impl;


import static com.health.domain.exception.ErrorCode.*;

import com.health.service.mealservice.dto.DailyMealServiceDto;
import com.health.domain.entity.DailyMealEntity;
import com.health.domain.entity.MealEntity;
import com.health.domain.entity.UserEntity;
import com.health.domain.exception.CustomException;
import com.health.domain.repository.ConsumeFoodRepository;
import com.health.domain.repository.DailyMealRepository;
import com.health.domain.repository.MealRepository;
import com.health.domain.repository.UserRepository;
import com.health.service.mealservice.service.DailyMealService;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DailyMealServiceImpl implements DailyMealService {

  private final UserRepository userRepository;
  private final MealRepository mealRepository;
  private final ConsumeFoodRepository consumeFoodRepository;
  private final DailyMealRepository dailyMealRepository;

  @Override
  @Transactional(readOnly = true)
  public Page<DailyMealServiceDto> getDailyMealList(String authId, Pageable pageable) {

    UserEntity findUser = findUserByAuthId(authId);

    Page<DailyMealEntity> mealList =
        dailyMealRepository.findByUserOrderByDailyMealDtDesc(findUser, pageable);

    return mealList.map(DailyMealServiceDto::fromEntity);
  }

  @Override
  public DailyMealServiceDto createDailyMeal(String authId, LocalDate dailyMealDt) {

    UserEntity findUser = findUserByAuthId(authId);

    validateIsExistDailyMeal(dailyMealDt, findUser);

    DailyMealEntity dailyMealEntity = DailyMealEntity.createNew(findUser, dailyMealDt);
    DailyMealEntity savedDailyMeal = dailyMealRepository.save(dailyMealEntity);

    return DailyMealServiceDto.fromEntity(savedDailyMeal);
  }

  @Override
  // nutrient 정보 업데이트 위해 기존 캐시 삭제
  @CacheEvict(cacheNames = "user", key = "@redisKeyComponent.intakeKey(#authId, #dailyMealDt)")
  public LocalDate deleteDailyMeal(String authId, LocalDate dailyMealDt) {

    UserEntity findUser = findUserByAuthId(authId);
    DailyMealEntity findDailyMeal = findDailyMealByUserAndDt(dailyMealDt, findUser);


    List<MealEntity> meals = findDailyMeal.getMeals();
    meals.forEach(meal -> consumeFoodRepository.deleteByMeals(meals));

    mealRepository.deleteByDailyMeal(findDailyMeal);
    dailyMealRepository.delete(findDailyMeal);


    return findDailyMeal.getDailyMealDt();
  }

  private UserEntity findUserByAuthId(String authId) {
    return userRepository.findByAuthId(authId)
        .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
  }

  private DailyMealEntity findDailyMealByUserAndDt(LocalDate dailyMealDt, UserEntity findUser) {
    return dailyMealRepository.findByUserAndDailyMealDt(findUser,
            dailyMealDt)
        .orElseThrow(() -> new CustomException(DAILY_MEAL_NOT_FOUND));
  }

  private void validateIsExistDailyMeal(LocalDate dailyMealDt, UserEntity findUser) {
    if (dailyMealRepository.existsByUserAndDailyMealDt(findUser, dailyMealDt)) {
      throw new CustomException(DAILY_MEAL_ALREADY_EXIST);
    }
  }
}
