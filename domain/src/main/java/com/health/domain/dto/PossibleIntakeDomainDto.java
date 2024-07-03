package com.health.domain.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PossibleIntakeDomainDto {


  private String authId;

  private LocalDate date;

  private Integer availKCal;
  private Integer intakeCalorie;

}
