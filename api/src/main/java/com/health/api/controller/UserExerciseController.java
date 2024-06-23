package com.health.api.controller;

import com.health.api.form.ExerciseRecordForm;
import com.health.api.service.ApiUserExerciseService;
import com.health.common.model.SuccessResponse;
import com.health.domain.dto.ExerciseRecordDomainDto;
import com.health.domain.response.ExerciseRecordResponse;
import com.health.security.authentication.AuthValidatorComponent;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/{authId}")
@RequiredArgsConstructor
public class UserExerciseController {

  private final AuthValidatorComponent authValidatorComponent;
  private final ApiUserExerciseService apiUserExerciseService;


  /**
   * exercise record list 조회하는 end point
   */
  @GetMapping("/exercise")
  public ResponseEntity<?> getExerciseList(@PathVariable String authId, Pageable pageable) {

    // 올바른 user의 접근인지 확인
    authValidatorComponent.validateAuthId(authId);

    Page<ExerciseRecordDomainDto> exerciseList =
        apiUserExerciseService.getExerciseList(authId, pageable);

    return ResponseEntity.ok(
        SuccessResponse.of(exerciseList.map(ExerciseRecordResponse::fromDomainDto))
    );
  }

  /**
   * exercise record 등록하는 end point
   */
  @PostMapping("/exercise")
  public ResponseEntity<?> createExerciseRecord(
      @PathVariable String authId, @Validated @RequestBody ExerciseRecordForm form) {

    // 올바른 user의 접근인지 확인
    authValidatorComponent.validateAuthId(authId);

    ExerciseRecordDomainDto exerciseRecordDto =
        apiUserExerciseService.createExerciseRecord(authId, form);

    return ResponseEntity.status(HttpStatus.CREATED).body(
        SuccessResponse.of(ExerciseRecordResponse.fromDomainDto(exerciseRecordDto))
    );
  }

  /**
   * exercise record 수정하는 end point
   */
  @PatchMapping("/exercise/{recordId}")
  public ResponseEntity<?> updateExerciseRecord(
      @PathVariable String authId, @PathVariable Long recordId,
      @RequestBody ExerciseRecordForm form) {

    // 올바른 user의 접근인지 확인
    authValidatorComponent.validateAuthId(authId);

    ExerciseRecordDomainDto updatedExerciseRecordDto =
        apiUserExerciseService.updateExerciseRecord(authId, recordId, form);

    return ResponseEntity.status(HttpStatus.CREATED).body(
        SuccessResponse.of(ExerciseRecordResponse.fromDomainDto(updatedExerciseRecordDto))
    );
  }

  /**
   * exercise record 삭제하는 end point
   */
  @DeleteMapping("/exercise/{recordId}")
  public ResponseEntity<?> deleteExerciseRecord(
      @PathVariable String authId, @PathVariable Long recordId
  ) {

    // 올바른 user의 접근인지 확인
    authValidatorComponent.validateAuthId(authId);

    apiUserExerciseService.deleteExerciseRecord(authId, recordId);

    return ResponseEntity.ok(SuccessResponse.of("Delete complete. recordId: " + recordId));
  }

}
