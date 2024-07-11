package com.health.mealservice.dto;

import com.health.domain.entity.MealEntity;
import com.health.domain.type.MealType;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MealServiceDto {

    private Long id;

    private MealType mealType;

    private NutrientServiceDto nutrient;

    private LocalDate mealDt;

    private DailyMealServiceDto dailyMeal;

    private List<ConsumeFoodServiceDto> consumeFoodList;

    public static MealServiceDto fromEntity(MealEntity mealEntity) {
        return MealServiceDto.builder()
            .id(mealEntity.getId())
            .mealType(mealEntity.getMealType())
            .mealDt(mealEntity.getMealDt())
            .nutrient(NutrientServiceDto.fromEntity(mealEntity.getNutrient()))
            .build();
    }
}
