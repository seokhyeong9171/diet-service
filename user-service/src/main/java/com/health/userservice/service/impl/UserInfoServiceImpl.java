package com.health.userservice.service.impl;

import static com.health.common.exception.ErrorCode.NICKNAME_DUPLICATED;
import static com.health.common.exception.ErrorCode.USER_NOT_FOUND;
import static com.health.userservice.util.UserCalorieUtil.calculateAbleCalorie;

import com.health.common.exception.CustomException;
import com.health.common.redis.RedisComponent;
import com.health.domain.dto.IntakeDomainDto;
import com.health.domain.dto.UserDomainDto;
import com.health.domain.entity.DailyMealEntity;
import com.health.domain.entity.UserEntity;
import com.health.domain.form.UserDetailsDomainForm;
import com.health.domain.form.UserNicknameDomainForm;
import com.health.domain.repository.DailyMealRepository;
import com.health.domain.repository.UserRepository;
import com.health.userservice.service.UserInfoService;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserInfoServiceImpl implements UserInfoService {

  private final UserRepository userRepository;
  private final DailyMealRepository dailyMealRepository;

  private final RedisComponent redisComponent;


  @Override
  @Transactional(readOnly = true)
  public UserDomainDto getUserInfo(String authId) {

    UserEntity findUser = findUserByAuthId(authId);

    return UserDomainDto.fromEntity(findUser);
  }

  @Override
  @Transactional(readOnly = true)
  @Cacheable(cacheNames = "user", key = "@redisKeyComponent.intakeKey(#authId, #date)")
  public IntakeDomainDto getIntakeInfo(String authId, LocalDate date) {

    UserEntity findUser = findUserByAuthId(authId);
    DailyMealEntity findDailyMeal = findTodayDailyMeal(findUser);

    Integer availKCal = calculateAbleCalorie(findUser);
    Integer intakeCalorie = getIntakeCalorie(findDailyMeal);

    return createIntakeDomainDto(findUser, availKCal, intakeCalorie);
  }

  @Override
  public UserDomainDto updateUserDetails(String authId, UserDetailsDomainForm form) {
    UserEntity findUser = findUserByAuthId(authId);

    findUser.updateDetails(form);

    return UserDomainDto.fromEntity(findUser);
  }

  @Override
  public String updateUserNickname(String authId, UserNicknameDomainForm form) {

    // 변경하려는 닉네임이 중복된 닉네임인지 확인
    validUniqueNickname(form);

    UserEntity findUser = findUserByAuthId(authId);
    findUser.updateNickname(form);

    return findUser.getNickname();
  }


  private UserEntity findUserByAuthId(String authId) {
    return userRepository.findByAuthId(authId)
        .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
  }

  private void validUniqueNickname(UserNicknameDomainForm form) {
    if (userRepository.existsByNickname(form.getNickname())) {
      throw new CustomException(NICKNAME_DUPLICATED);
    }
  }

  private DailyMealEntity findTodayDailyMeal(UserEntity findUser) {
    return dailyMealRepository
        .findByUserAndDailyMealDt(findUser, LocalDate.now())
        .orElse(null);
  }

  private Integer getIntakeCalorie(DailyMealEntity dailyMeal) {
    return dailyMeal == null ? 0 : dailyMeal.getNutrient().getKCal();
  }

  private IntakeDomainDto createIntakeDomainDto
      (UserEntity findUser, Integer availKCal, Integer intakeCalorie) {

    return IntakeDomainDto.builder()
        .authId(findUser.getAuthId())
        .availKCal(availKCal).intakeCalorie(intakeCalorie)
        .build();
  }
}
