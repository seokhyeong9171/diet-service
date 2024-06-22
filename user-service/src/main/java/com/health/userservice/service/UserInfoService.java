package com.health.userservice.service;


import com.health.domain.dto.UserDomainDto;
import com.health.domain.form.UserDetailsDomainForm;
import com.health.domain.form.UserNicknameDomainForm;

public interface UserInfoService {

  UserDomainDto getUserInfo(String authId);

  UserDomainDto updateUserDetails(String authId, UserDetailsDomainForm form);

  String updateUserNickname(String authId, UserNicknameDomainForm form);
}
