package com.health.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DailyMealDomainDto {

    private Long id;

    private NutrientDomainDto nutrientDomainDto;

    private UserDomainDto user;

    private List<MealDomainDto> meals;
}
