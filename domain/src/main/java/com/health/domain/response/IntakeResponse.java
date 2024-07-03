package com.health.domain.response;

import com.health.domain.dto.IntakeDomainDto;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IntakeResponse {

  private Integer availKCal;
  private Integer intakeCalorie;

  private Double intakeRatio;

  public static IntakeResponse fromDomainDto(IntakeDomainDto domainDto) {
    return IntakeResponse.builder()
        .availKCal(domainDto.getAvailKCal())
        .intakeCalorie(domainDto.getIntakeCalorie())
        .intakeRatio((double) (domainDto.getIntakeCalorie() * 100) / domainDto.getAvailKCal())
        .build();
  }
}
