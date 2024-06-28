package com.health.mealservice.service.impl;

import static com.health.common.exception.ErrorCode.*;

import com.health.common.exception.CustomException;
import com.health.domain.dto.DailyMealDomainDto;
import com.health.domain.dto.MealDomainDto;
import com.health.domain.entity.DailyMealEntity;
import com.health.domain.entity.UserEntity;
import com.health.domain.repository.DailyMealRepository;
import com.health.domain.repository.UserRepository;
import com.health.mealservice.service.DailyMealService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DailyMealServiceImpl implements DailyMealService {

  private final UserRepository userRepository;
  private final DailyMealRepository dailyMealRepository;

  @Override
  @Transactional(readOnly = true)
  public Page<DailyMealDomainDto> getDailyMealList(String authId, Pageable pageable) {

    UserEntity findUser = findUserByAuthId(authId);

    Page<DailyMealEntity> mealList = dailyMealRepository.findByUserOrderByDailyMealDtDesc(
        findUser, pageable);

    return mealList.map(DailyMealDomainDto::fromEntity);
  }

  private UserEntity findUserByAuthId(String authId) {
    return userRepository.findByAuthId(authId)
        .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
  }
}
