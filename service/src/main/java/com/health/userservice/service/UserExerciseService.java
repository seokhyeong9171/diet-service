package com.health.userservice.service;

import com.health.userservice.dto.ExerciseRecordServiceDto;
import com.health.userservice.form.ExerciseRecordServiceForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserExerciseService {

  Page<ExerciseRecordServiceDto> getExerciseList(String authId, Pageable pageable);

  ExerciseRecordServiceDto createExerciseRecord(String authId, ExerciseRecordServiceForm serviceForm);

  ExerciseRecordServiceDto updateExerciseRecord
      (String authId, Long recordId, ExerciseRecordServiceForm serviceForm);

  void deleteExerciseRecord(String authId, Long recordId);

}
