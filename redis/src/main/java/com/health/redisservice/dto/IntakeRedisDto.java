package com.health.redisservice.dto;

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
public class IntakeRedisDto {


  private String authId;

  private Integer availKCal;
  private Integer intakeCalorie;

}
