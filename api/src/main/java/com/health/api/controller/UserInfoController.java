package com.health.api.controller;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;

import com.health.api.form.UserDetailsForm;
import com.health.api.form.UserNicknameForm;
import com.health.api.application.UserInfoApplication;
import com.health.api.model.SuccessResponse;
import com.health.service.userservice.dto.UserServiceDto;
import com.health.service.mealservice.response.IntakeResponse;
import com.health.service.userservice.dto.IntakeServiceDto;
import com.health.service.userservice.response.UserInfoResponse;
import com.health.security.authentication.AuthValidatorComponent;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class UserInfoController {

  private final AuthValidatorComponent authValidatorComponent;
  private final UserInfoApplication userInfoApplication;

  /**
   * user info 조회 end point
   */
  @GetMapping
  public ResponseEntity<?> getUserInfo(@CookieValue(name = "Authorization") String jwt) {

    String authId = authValidatorComponent.validateAuthId(jwt);

    UserServiceDto userInfoDto = userInfoApplication.getUserInfo(authId);

    return ResponseEntity.ok(
        SuccessResponse.of(UserInfoResponse.fromDomainDto(userInfoDto))
    );
  }

  @GetMapping("/intake")
  public ResponseEntity<?> getIntakeInfo(
      @CookieValue(name = "Authorization") String jwt,
      @RequestParam @DateTimeFormat(iso = DATE)LocalDate date
  ) {

    String authId = authValidatorComponent.validateAuthId(jwt);

    IntakeServiceDto possibleIntake = userInfoApplication.getIntakeInfo(authId, date);

    return ResponseEntity.ok(
        SuccessResponse.of(IntakeResponse.fromDomainDto(possibleIntake))
    );
  }

  /**
   * user nickname 수정 end point
   */
  @PatchMapping("/nickname")
  public ResponseEntity<?> updateUserNickname(
      @CookieValue(name = "Authorization") String jwt, @RequestBody UserNicknameForm form) {

    String authId = authValidatorComponent.validateAuthId(jwt);

    String updatedNickname = userInfoApplication.updateUserNickname(authId, form);

    return ResponseEntity.ok(
        SuccessResponse.of(updatedNickname)
    );
  }

  /**
   * user detail 수정 end point
   */
  @PatchMapping("/userinfo")
  public ResponseEntity<?> updateUserDetails(
      @CookieValue(name = "Authorization") String jwt, @RequestBody UserDetailsForm form) {

    String authId = authValidatorComponent.validateAuthId(jwt);

    UserServiceDto userInfoDto = userInfoApplication.updateUserInfo(authId, form);

    return ResponseEntity.ok(
        SuccessResponse.of(UserInfoResponse.fromDomainDto(userInfoDto))
    );
  }


}
