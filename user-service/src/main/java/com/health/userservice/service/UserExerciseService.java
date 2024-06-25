package com.health.userservice.service;

import com.health.domain.dto.ExerciseRecordDomainDto;
import com.health.domain.form.ExerciseRecordDomainForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserExerciseService {

  Page<ExerciseRecordDomainDto> getExerciseList(String authId, Pageable pageable);

  ExerciseRecordDomainDto createExerciseRecord(String authId, ExerciseRecordDomainForm form);

  ExerciseRecordDomainDto updateExerciseRecord
      (String authId, Long recordId, ExerciseRecordDomainForm form);

  void deleteExerciseRecord(String authId, Long recordId);

}
