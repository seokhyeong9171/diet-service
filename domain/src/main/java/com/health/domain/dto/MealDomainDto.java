package com.health.domain.dto;

import com.health.domain.entity.MealEntity;
import com.health.domain.form.MealDomainForm;
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
public class MealDomainDto {

    private Long id;

    private MealType mealType;

    private NutrientDomainDto nutrient;

    private LocalDate mealDt;

    private DailyMealDomainDto dailyMeal;

    private List<ConsumeFoodDomainDto> consumeFoodList;

    public static MealDomainDto fromEntity(MealEntity mealEntity) {
        return MealDomainDto.builder()
            .id(mealEntity.getId())
            .mealType(mealEntity.getMealType())
            .mealDt(mealEntity.getMealDt())
            .nutrient(NutrientDomainDto.fromEntity(mealEntity.getNutrient()))
            .build();
    }
}
