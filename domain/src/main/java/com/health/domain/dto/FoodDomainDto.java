package com.health.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FoodDomainDto {

    private String foodCode;

    private String foodName;

    private Integer foodAmount;

    private NutrientDomainDto nutrientDomainDto;

}
