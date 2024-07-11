package com.health.userservice.service;

import com.health.domain.dto.ExerciseRecordDomainDto;
import com.health.userservice.form.ExerciseRecordServiceForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserExerciseService {

  Page<ExerciseRecordDomainDto> getExerciseList(String authId, Pageable pageable);

  ExerciseRecordDomainDto createExerciseRecord(String authId, ExerciseRecordServiceForm form);

  ExerciseRecordDomainDto updateExerciseRecord
      (String authId, Long recordId, ExerciseRecordServiceForm form);

  void deleteExerciseRecord(String authId, Long recordId);

}
