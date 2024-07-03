package com.health.domain.response;

import com.health.domain.dto.PossibleIntakeDomainDto;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PossibleIntakeResponse {


  private LocalDate date;

  private Integer availKCal;
  private Integer intakeCalorie;

  private Double intakeRatio;

  public static PossibleIntakeResponse fromDomainDto(PossibleIntakeDomainDto domainDto) {
    return PossibleIntakeResponse.builder()
        .date(domainDto.getDate())
        .availKCal(domainDto.getAvailKCal())
        .intakeCalorie(domainDto.getIntakeCalorie())
        .intakeRatio((double) (domainDto.getIntakeCalorie() * 100) / domainDto.getAvailKCal())
        .build();
  }
}
