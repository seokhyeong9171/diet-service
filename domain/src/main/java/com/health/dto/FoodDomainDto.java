package com.health.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FoodDomainDto {

    private Long id;

    private String foodName;

    private Integer foodAmount;

    private NutrientDomainDto nutrientDomainDto;

}
