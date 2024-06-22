package com.health.domain.dto;

import com.health.domain.type.MealType;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MealDomainDto {

    private Long id;

    private MealType mealType;

    private NutrientDomainDto nutrientDomainDto;

    private LocalDate mealDt;

    private DailyMealDomainDto dailyMeal;

    private List<ConsumeFoodDomainDto> consumeFoodList;
}
