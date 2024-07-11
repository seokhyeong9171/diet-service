package com.health.service.mealservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemDto {

  private String foodCode;

  private String name;

  private Integer weight;

  private Integer kCal;

  private Integer protein;

  private Integer fat;

  private Integer carbohydrate;

}
