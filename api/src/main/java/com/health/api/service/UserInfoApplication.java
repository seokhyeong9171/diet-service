package com.health.api.service;

import com.health.api.form.UserDetailsForm;
import com.health.api.form.UserNicknameForm;
import com.health.domain.dto.PossibleIntakeDomainDto;
import com.health.domain.dto.UserDomainDto;
import com.health.userservice.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserInfoApplication {

  private final UserInfoService userInfoService;

  public UserDomainDto getUserInfo(String authId) {

    // user-service module로 넘김
    return userInfoService.getUserInfo(authId);
  }

  public PossibleIntakeDomainDto getIntakeInfo(String authId) {
    return userInfoService.getIntakeInfo(authId);
  }

  public UserDomainDto updateUserInfo(String authId, UserDetailsForm form) {

    // domain form으로 변환해서 user-service module로 넘김
    return userInfoService.updateUserDetails(authId, form.toDomainForm());
  }

  public String updateUserNickname(String authId, UserNicknameForm form) {

    // domain form으로 변환해서 user-service module로 넘김
    return userInfoService.updateUserNickname(authId, form.toDomainForm());
  }
}
