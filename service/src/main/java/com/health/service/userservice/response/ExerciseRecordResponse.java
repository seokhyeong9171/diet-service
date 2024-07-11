package com.health.service.userservice.response;

import com.health.service.userservice.dto.ExerciseRecordServiceDto;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExerciseRecordResponse {

  private LocalDate exerciseRecDt;

  private String description;

  public static ExerciseRecordResponse fromDomainDto(ExerciseRecordServiceDto dto) {
    return ExerciseRecordResponse.builder()
        .exerciseRecDt(dto.getExerciseRecDt())
        .description(dto.getDescription())
        .build();
  }

}
