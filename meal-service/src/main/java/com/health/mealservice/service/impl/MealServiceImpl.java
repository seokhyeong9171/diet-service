package com.health.mealservice.service.impl;

import static com.health.common.exception.ErrorCode.*;

import com.health.common.exception.CustomException;
import com.health.common.exception.ErrorCode;
import com.health.domain.dto.MealDomainDto;
import com.health.domain.entity.DailyMealEntity;
import com.health.domain.entity.MealEntity;
import com.health.domain.entity.UserEntity;
import com.health.domain.form.MealDomainForm;
import com.health.domain.repository.DailyMealRepository;
import com.health.domain.repository.FoodRepository;
import com.health.domain.repository.MealRepository;
import com.health.domain.repository.UserRepository;
import com.health.mealservice.service.MealService;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MealServiceImpl implements MealService {

  private final UserRepository userRepository;
  private final FoodRepository foodRepository;
  private final MealRepository mealRepository;
  private final DailyMealRepository dailyMealRepository;

  @Override
  public MealDomainDto createMeal(String authId, MealDomainForm domainForm) {

    UserEntity findUser = findUserByAuthId(authId);
    LocalDate mealConsumeDate = domainForm.getConsumeDt();

    DailyMealEntity dailyMealEntity = getUserDailyMeal(findUser, mealConsumeDate);

    MealEntity mealEntity = MealEntity.createNew(dailyMealEntity, domainForm);
    MealEntity savedMealEntity = mealRepository.save(mealEntity);

    dailyMealEntity.getMeals().add(savedMealEntity);

    return MealDomainDto.fromEntity(mealEntity);
  }


  private UserEntity findUserByAuthId(String authId) {
    return userRepository.findByAuthId(authId)
        .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
  }

  private DailyMealEntity getUserDailyMeal(UserEntity findUser, LocalDate mealConsumeDate) {
    return dailyMealRepository.findByUserAndDailyMealDt(findUser, mealConsumeDate)
        .orElseGet(() -> {
          DailyMealEntity mealEntity = DailyMealEntity.createNew(findUser, mealConsumeDate);
          return dailyMealRepository.save(mealEntity);
        });
  }
}
