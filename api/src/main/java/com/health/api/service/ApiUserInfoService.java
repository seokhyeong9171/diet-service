package com.health.api.service;

import com.health.api.form.UserDetailsForm;
import com.health.api.form.UserNicknameForm;
import com.health.domain.dto.UserDomainDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.health.userservice.service.UserInfoService;

@Service
@Transactional
@RequiredArgsConstructor
public class ApiUserInfoService {

  private final UserInfoService userInfoService;

  public UserDomainDto getUserInfo(String authId) {

    // user-service module로 넘김
    return userInfoService.getUserInfo(authId);
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
