package com.health.mealservice.response;

import com.health.redisservice.dto.IntakeRedisDto;
import com.health.userservice.dto.IntakeServiceDto;
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

  public static IntakeResponse fromDomainDto(IntakeServiceDto domainDto) {
    return IntakeResponse.builder()
        .availKCal(domainDto.getAvailKCal())
        .intakeCalorie(domainDto.getIntakeCalorie())
        .intakeRatio((double) (domainDto.getIntakeCalorie() * 100) / domainDto.getAvailKCal())
        .build();
  }
}
