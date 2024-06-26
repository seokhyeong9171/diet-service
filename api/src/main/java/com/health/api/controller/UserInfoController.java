package com.health.api.controller;

import com.health.api.form.UserDetailsForm;
import com.health.api.form.UserNicknameForm;
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
@RequestMapping("{authId}/mypage")
@RequiredArgsConstructor
public class UserInfoController {

  private final AuthValidatorComponent authValidatorComponent;
  private final ApiUserInfoService apiUserInfoService;

  /**
   * user info 조회 end point
   */
  @GetMapping
  public ResponseEntity<?> getUserInfo(@PathVariable String authId) {

    authValidatorComponent.validateAuthId(authId);

    UserDomainDto userInfoDto = apiUserInfoService.getUserInfo(authId);

    return ResponseEntity.ok(
        SuccessResponse.of(UserInfoResponse.fromDomainDto(userInfoDto))
    );
  }

  /**
   * user nickname 수정 end point
   */
  @PatchMapping("/nickname")
  public ResponseEntity<?> updateUserNickname(
      @PathVariable String authId, @RequestBody UserNicknameForm form) {

    authValidatorComponent.validateAuthId(authId);

    String updatedNickname = apiUserInfoService.updateUserNickname(authId, form);

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

    UserDomainDto userInfoDto = apiUserInfoService.updateUserInfo(authId, form);

    return ResponseEntity.ok(
        SuccessResponse.of(UserInfoResponse.fromDomainDto(userInfoDto))
    );
  }


}
