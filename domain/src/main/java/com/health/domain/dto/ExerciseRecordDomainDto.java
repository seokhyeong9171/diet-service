package com.health.domain.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExerciseRecordDomainDto {

    private Long id;

    private LocalDate exerciseRecDt;

    private String description;

    private UserDomainDto user;

}
