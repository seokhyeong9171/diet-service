package com.health.api.controller;

import static org.springframework.http.HttpStatus.CREATED;

import com.health.api.form.ExerciseRecordForm;
import com.health.api.service.UserExerciseApplication;
import com.health.common.model.SuccessResponse;
import com.health.domain.dto.ExerciseRecordDomainDto;
import com.health.domain.response.ExerciseRecordResponse;
import com.health.security.authentication.AuthValidatorComponent;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exercise")
@RequiredArgsConstructor
public class UserExerciseController {

  private final AuthValidatorComponent authValidatorComponent;
  private final UserExerciseApplication userExerciseApplication;


  /**
   * exercise record list 조회하는 end point
   */
  @GetMapping
  public ResponseEntity<?> getExerciseList(
      @CookieValue(name = "Authorization") String jwt, Pageable pageable
  ) {

    // 올바른 user의 접근인지 확인
    String authId = authValidatorComponent.validateAuthId(jwt);

    Page<ExerciseRecordDomainDto> exerciseList =
        userExerciseApplication.getExerciseList(authId, pageable);

    return ResponseEntity.ok(
        SuccessResponse.of(exerciseList.map(ExerciseRecordResponse::fromDomainDto))
    );
  }

  /**
   * exercise record 등록하는 end point
   */
  @PostMapping
  public ResponseEntity<?> createExerciseRecord(
      @CookieValue(name = "Authorization") String jwt,
      @Validated @RequestBody ExerciseRecordForm form
  ) {

    // 올바른 user의 접근인지 확인
    String authId = authValidatorComponent.validateAuthId(jwt);

    ExerciseRecordDomainDto exerciseRecordDto =
        userExerciseApplication.createExerciseRecord(authId, form);

    return ResponseEntity.status(CREATED).body(
        SuccessResponse.of(ExerciseRecordResponse.fromDomainDto(exerciseRecordDto))
    );
  }

  /**
   * exercise record 수정하는 end point
   */
  @PatchMapping("/{recordId}")
  public ResponseEntity<?> updateExerciseRecord(
      @CookieValue(name = "Authorization") String jwt, @PathVariable Long recordId,
      @RequestBody ExerciseRecordForm form) {

    // 올바른 user의 접근인지 확인
    String authId = authValidatorComponent.validateAuthId(jwt);

    ExerciseRecordDomainDto updatedExerciseRecordDto =
        userExerciseApplication.updateExerciseRecord(authId, recordId, form);

    return ResponseEntity.status(CREATED).body(
        SuccessResponse.of(ExerciseRecordResponse.fromDomainDto(updatedExerciseRecordDto))
    );
  }

  /**
   * exercise record 삭제하는 end point
   */
  @DeleteMapping("/{recordId}")
  public ResponseEntity<?> deleteExerciseRecord(
      @CookieValue(name = "Authorization") String jwt, @PathVariable Long recordId
  ) {

    // 올바른 user의 접근인지 확인
    String authId = authValidatorComponent.validateAuthId(jwt);

    userExerciseApplication.deleteExerciseRecord(authId, recordId);

    return ResponseEntity.ok(SuccessResponse.of("Delete complete. recordId: " + recordId));
  }

}
