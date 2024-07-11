package com.health.userservice.service;


import com.health.userservice.dto.UserServiceDto;
import com.health.userservice.dto.IntakeServiceDto;
import com.health.userservice.form.UserDetailsServiceForm;
import com.health.userservice.form.UserNicknameServiceForm;
import java.time.LocalDate;

public interface UserInfoService {

  UserServiceDto getUserInfo(String authId);

  UserServiceDto updateUserDetails(String authId, UserDetailsServiceForm serviceForm);

  String updateUserNickname(String authId, UserNicknameServiceForm serviceForm);

  IntakeServiceDto getIntakeInfo(String authId, LocalDate date);
}
