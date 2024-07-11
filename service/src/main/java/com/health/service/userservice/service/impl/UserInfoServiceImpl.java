package com.health.service.userservice.service.impl;

import static com.health.domain.exception.ErrorCode.*;

import com.health.service.userservice.dto.UserServiceDto;
import com.health.service.userservice.form.UserDetailsServiceForm;
import com.health.service.userservice.form.UserNicknameServiceForm;
import com.health.service.userservice.service.UserInfoService;
import com.health.service.userservice.util.UserCalorieUtil;
import com.health.domain.entity.DailyMealEntity;
import com.health.domain.entity.UserEntity;
import com.health.domain.exception.CustomException;
import com.health.service.userservice.dto.IntakeServiceDto;
import com.health.domain.repository.DailyMealRepository;
import com.health.domain.repository.UserRepository;
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



  @Override
  @Transactional(readOnly = true)
  public UserServiceDto getUserInfo(String authId) {

    UserEntity findUser = findUserByAuthId(authId);

    return UserServiceDto.fromEntity(findUser);
  }

  @Override
  @Transactional(readOnly = true)
  @Cacheable(cacheNames = "user", key = "@redisKeyComponent.intakeKey(#authId, #date)")
  public IntakeServiceDto getIntakeInfo(String authId, LocalDate date) {

    UserEntity findUser = findUserByAuthId(authId);
    DailyMealEntity findDailyMeal = findTodayDailyMeal(findUser);

    Integer availKCal = UserCalorieUtil.calculateAbleCalorie(findUser);
    Integer intakeCalorie = getIntakeCalorie(findDailyMeal);

    return createIntakeDomainDto(findUser, availKCal, intakeCalorie);
  }

  @Override
  public UserServiceDto updateUserDetails(String authId, UserDetailsServiceForm serviceForm) {
    UserEntity findUser = findUserByAuthId(authId);

    findUser.updateDetails(serviceForm.toDomainForm());

    return UserServiceDto.fromEntity(findUser);
  }

  @Override
  public String updateUserNickname(String authId, UserNicknameServiceForm serviceForm) {

    // 변경하려는 닉네임이 중복된 닉네임인지 확인
    validUniqueNickname(serviceForm);

    UserEntity findUser = findUserByAuthId(authId);
    findUser.updateNickname(serviceForm.toDomainForm());

    return findUser.getNickname();
  }


  private UserEntity findUserByAuthId(String authId) {
    return userRepository.findByAuthId(authId)
        .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
  }

  private void validUniqueNickname(UserNicknameServiceForm form) {
    if (userRepository.existsByNickname(form.getNickname())) {
      throw new CustomException(USER_NICKNAME_DUPLICATED);
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

  private IntakeServiceDto createIntakeDomainDto
      (UserEntity findUser, Integer availKCal, Integer intakeCalorie) {

    return IntakeServiceDto.builder()
        .authId(findUser.getAuthId())
        .availKCal(availKCal).intakeCalorie(intakeCalorie)
        .build();
  }
}
