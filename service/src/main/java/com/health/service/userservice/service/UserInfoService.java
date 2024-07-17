package com.health.service.userservice.service;


import com.health.service.userservice.dto.IntakeServiceDto;
import com.health.service.userservice.dto.UserServiceDto;
import com.health.service.userservice.form.UserDetailsServiceForm;
import com.health.service.userservice.form.UserNicknameServiceForm;
import java.time.LocalDate;

public interface UserInfoService {

  UserServiceDto getUserInfo(String authId);

  UserServiceDto updateUserDetails(String authId, UserDetailsServiceForm serviceForm);

  String updateUserNickname(String authId, UserNicknameServiceForm serviceForm);

  IntakeServiceDto getIntakeInfo(String authId, LocalDate date);
}
