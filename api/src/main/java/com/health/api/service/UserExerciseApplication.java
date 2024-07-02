package com.health.api.service;

import com.health.api.form.ExerciseRecordForm;
import com.health.domain.dto.ExerciseRecordDomainDto;
import com.health.userservice.service.UserExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserExerciseApplication {

  private final UserExerciseService userExerciseService;

  public Page<ExerciseRecordDomainDto> getExerciseList(String authId, Pageable pageable) {

    // user-service module로 넘김
    return userExerciseService.getExerciseList(authId, pageable);
  }

  public ExerciseRecordDomainDto createExerciseRecord(String authId, ExerciseRecordForm form) {

    // domain form으로 변환해서 user-service module로 넘김
    return userExerciseService.createExerciseRecord(authId, form.toDomainForm());
  }

  public ExerciseRecordDomainDto updateExerciseRecord
      (String authId, Long recordId, ExerciseRecordForm form) {

    // domain form으로 변환해서 user-service module로 넘김
    return userExerciseService.updateExerciseRecord(authId, recordId, form.toDomainForm());
  }

  public void deleteExerciseRecord(String authId, Long recordId) {

    // user-service module로 넘김
    userExerciseService.deleteExerciseRecord(authId, recordId);
  }


}
