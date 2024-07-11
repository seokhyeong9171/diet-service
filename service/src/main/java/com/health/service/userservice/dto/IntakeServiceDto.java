package com.health.service.userservice.dto;

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
public class IntakeServiceDto {


  private String authId;

  private Integer availKCal;
  private Integer intakeCalorie;

}
