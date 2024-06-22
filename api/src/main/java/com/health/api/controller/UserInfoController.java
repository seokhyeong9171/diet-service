package com.health.api.controller;

import com.health.api.form.UserInfoForm;
import com.health.api.service.ApiUserInfoService;
import com.health.common.model.SuccessResponse;
import com.health.domain.dto.UserDomainDto;
import com.health.domain.response.UserInfoResponse;
import com.health.security.authentication.AuthValidatorComponent;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("{authId}")
@RequiredArgsConstructor
public class UserInfoController {

  private final AuthValidatorComponent authValidatorComponent;
  private final ApiUserInfoService apiUserInfoService;

  @GetMapping("/mypage")
  public ResponseEntity<?> getUserInfo(@PathVariable String authId) {

    authValidatorComponent.validateAuthId(authId);

    UserDomainDto userInfoDto = apiUserInfoService.getUserInfo(authId);

    return ResponseEntity.ok(
        SuccessResponse.of(UserInfoResponse.fromDomainDto(userInfoDto))
    );
  }

  @PatchMapping("/userinfo")
  public ResponseEntity<?> updateUserInfo(
      @PathVariable String authId, @RequestBody UserInfoForm form) {

    authValidatorComponent.validateAuthId(authId);

    UserDomainDto userInfoDto = apiUserInfoService.updateUserInfo(authId, form);

    return ResponseEntity.ok(
        SuccessResponse.of(UserInfoResponse.fromDomainDto(userInfoDto))
    );
  }

}
