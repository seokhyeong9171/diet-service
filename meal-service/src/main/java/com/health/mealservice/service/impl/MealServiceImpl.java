package com.health.mealservice.service.impl;

import static com.health.common.exception.ErrorCode.*;

import com.health.common.exception.CustomException;
import com.health.domain.dto.MealDomainDto;
import com.health.domain.entity.DailyMealEntity;
import com.health.domain.entity.MealEntity;
import com.health.domain.entity.UserEntity;
import com.health.domain.form.MealDomainForm;
import com.health.domain.repository.DailyMealRepository;
import com.health.domain.repository.MealRepository;
import com.health.domain.repository.UserRepository;
import com.health.mealservice.service.MealService;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MealServiceImpl implements MealService {

  private final UserRepository userRepository;
  private final MealRepository mealRepository;
  private final DailyMealRepository dailyMealRepository;

  @Override
  @Transactional(readOnly = true)
  public List<MealDomainDto> getMealList(String authId, LocalDate dailyMealDt) {
    UserEntity findUser = findUserByAuthId(authId);
    DailyMealEntity findDailyMeal = findDailyMealByUserAndDt(findUser, dailyMealDt);

    return findDailyMeal.getMeals().stream()
        .map(MealDomainDto::fromEntity).toList();
  }

  @Override
  @Transactional(readOnly = true)
  public MealDomainDto getMealInfo(String authId, LocalDate dailyMealDt, Long mealId) {

    UserEntity findUser = findUserByAuthId(authId);
    DailyMealEntity findDailyMeal = findDailyMealByUserAndDt(findUser, dailyMealDt);

    MealEntity findMeal = findMealById(mealId);

    validateProperMeal(findMeal, findDailyMeal);

    return MealDomainDto.fromEntity(findMeal);
  }

  @Override
  public MealDomainDto createMeal(String authId, LocalDate dailyMealDt, MealDomainForm domainForm) {

    UserEntity findUser = findUserByAuthId(authId);

    DailyMealEntity dailyMealEntity = findDailyMealByUserAndDt(findUser, dailyMealDt);

    MealEntity mealEntity = MealEntity.createNew(dailyMealEntity, dailyMealDt, domainForm);
    MealEntity savedMealEntity = mealRepository.save(mealEntity);

    dailyMealEntity.getMeals().add(savedMealEntity);

    return MealDomainDto.fromEntity(mealEntity);
  }


  @Override
  public Long deleteMeal(String authId, LocalDate dailyMealDt, Long mealId) {
    UserEntity findUser = findUserByAuthId(authId);

    DailyMealEntity findDailyMeal = findDailyMealByUserAndDt(findUser, dailyMealDt);

    MealEntity findMeal = findMealById(mealId);

    validateProperMeal(findMeal, findDailyMeal);

    // TODO
    //  추후 food service 개발 후 관련된 ConsumeFood도 삭제

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
