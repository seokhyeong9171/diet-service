package com.health.domain.response;

import com.health.domain.dto.ExerciseRecordDomainDto;
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

  public static ExerciseRecordResponse fromDomainDto(ExerciseRecordDomainDto dto) {
    return ExerciseRecordResponse.builder()
        .exerciseRecDt(dto.getExerciseRecDt())
        .description(dto.getDescription())
        .build();
  }

}
