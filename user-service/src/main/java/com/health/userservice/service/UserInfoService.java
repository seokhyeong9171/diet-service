package com.health.userservice.service;


import com.health.domain.dto.UserDomainDto;
import com.health.domain.form.UserInfoDomainForm;

public interface UserInfoService {

  UserDomainDto getUserInfo(String authId);

  UserDomainDto updateUserInfo(String authId, UserInfoDomainForm form);

}
