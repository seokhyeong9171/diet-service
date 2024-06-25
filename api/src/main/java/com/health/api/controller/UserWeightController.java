package com.health.api.controller;

import static org.springframework.http.HttpStatus.CREATED;

import com.health.api.form.UserWeightForm;
import com.health.api.service.ApiUserWeightService;
import com.health.common.model.SuccessResponse;
import com.health.domain.dto.UserWeightDomainDto;
import com.health.domain.response.UserWeightResponse;
import com.health.security.authentication.AuthValidatorComponent;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/{authId}/weight")
@RequiredArgsConstructor
public class UserWeightController {

  private final AuthValidatorComponent authValidatorComponent;
  private final ApiUserWeightService apiUserWeightService;

  @GetMapping
  public ResponseEntity<?> getWeightList
      (@PathVariable String authId, @RequestParam String scope, Pageable pageable) {

    authValidatorComponent.validateAuthId(authId);

    Page<UserWeightResponse> weightResponseList =
        apiUserWeightService.getUserWeightList(authId, scope, pageable)
            .map(UserWeightResponse::fromDomainDto);

    return ResponseEntity.ok(SuccessResponse.of(weightResponseList));
  }

  @PostMapping
  public ResponseEntity<?> createWeightRecord(
      @PathVariable String authId, @RequestBody UserWeightForm form) {

    authValidatorComponent.validateAuthId(authId);

    UserWeightDomainDto userWeightDto = apiUserWeightService.createWeightRecord(authId, form);

    return ResponseEntity.status(CREATED).body(
        SuccessResponse.of(UserWeightResponse.fromDomainDto(userWeightDto))
    );
  }

  @PatchMapping("/{recordId}")
  public ResponseEntity<?> updateWeightRecord(
      @PathVariable String authId, @PathVariable Long recordId, @RequestBody UserWeightForm form
  ) {

    authValidatorComponent.validateAuthId(authId);

    UserWeightDomainDto userWeightDto =
        apiUserWeightService.updateWeightRecord(authId, recordId, form);

    return ResponseEntity.ok(
        SuccessResponse.of(UserWeightResponse.fromDomainDto(userWeightDto))
    );
  }

  @DeleteMapping("/{recordId}")
  public ResponseEntity<?> deleteWeightRecord(
      @PathVariable String authId, @PathVariable Long recordId
  ) {

    authValidatorComponent.validateAuthId(authId);

    Long deletedRecordId = apiUserWeightService.deleteWeightRecord(authId, recordId);

    return ResponseEntity.ok(
        SuccessResponse.of("delete weight record complete. recordId: " + deletedRecordId)
    );
  }


}
