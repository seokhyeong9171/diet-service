package com.health.userservice.service;


import com.health.domain.dto.IntakeDomainDto;
import com.health.domain.dto.UserDomainDto;
import com.health.userservice.form.UserDetailsServiceForm;
import com.health.userservice.form.UserNicknameServiceForm;
import java.time.LocalDate;

public interface UserInfoService {

  UserDomainDto getUserInfo(String authId);

  UserDomainDto updateUserDetails(String authId, UserDetailsServiceForm form);

  String updateUserNickname(String authId, UserNicknameServiceForm form);

  IntakeDomainDto getIntakeInfo(String authId, LocalDate date);
}
