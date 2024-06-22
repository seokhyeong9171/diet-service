package com.health.api.service;

import com.health.api.form.UserInfoForm;
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
    return userInfoService.getUserInfo(authId);
  }

  public UserDomainDto updateUserInfo(String authId, UserInfoForm form) {
    return userInfoService.updateUserInfo(authId, form.toDomainForm());
  }


}
