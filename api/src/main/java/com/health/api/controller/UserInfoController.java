package com.health.api.controller;

import static org.springframework.format.annotation.DateTimeFormat.ISO.*;

import com.health.api.form.UserDetailsForm;
import com.health.api.form.UserNicknameForm;
import com.health.api.service.UserInfoApplication;
import com.health.common.model.SuccessResponse;
import com.health.domain.dto.IntakeDomainDto;
import com.health.domain.dto.UserDomainDto;
import com.health.domain.response.IntakeResponse;
import com.health.domain.response.UserInfoResponse;
import com.health.security.authentication.AuthValidatorComponent;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authid{authId}/mypage")
@RequiredArgsConstructor
public class UserInfoController {

  private final AuthValidatorComponent authValidatorComponent;
  private final UserInfoApplication userInfoApplication;

  /**
   * user info 조회 end point
   */
  @GetMapping
  public ResponseEntity<?> getUserInfo(@PathVariable String authId) {

    authValidatorComponent.validateAuthId(authId);

    UserDomainDto userInfoDto = userInfoApplication.getUserInfo(authId);

    return ResponseEntity.ok(
        SuccessResponse.of(UserInfoResponse.fromDomainDto(userInfoDto))
    );
  }

  @GetMapping("/intake")
  public ResponseEntity<?> getIntakeInfo(
      @PathVariable String authId,
      @RequestParam @DateTimeFormat(iso = DATE)LocalDate date
  ) {

    authValidatorComponent.validateAuthId(authId);

    IntakeDomainDto possibleIntake = userInfoApplication.getIntakeInfo(authId, date);

    return ResponseEntity.ok(
        SuccessResponse.of(IntakeResponse.fromDomainDto(possibleIntake))
    );
  }

  /**
   * user nickname 수정 end point
   */
  @PatchMapping("/nickname")
  public ResponseEntity<?> updateUserNickname(
      @PathVariable String authId, @RequestBody UserNicknameForm form) {

    authValidatorComponent.validateAuthId(authId);

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
      @PathVariable String authId, @RequestBody UserDetailsForm form) {

    authValidatorComponent.validateAuthId(authId);

    UserDomainDto userInfoDto = userInfoApplication.updateUserInfo(authId, form);

    return ResponseEntity.ok(
        SuccessResponse.of(UserInfoResponse.fromDomainDto(userInfoDto))
    );
  }


}
