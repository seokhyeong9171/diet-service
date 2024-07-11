package com.health.mealservice.dto;

import com.health.domain.entity.Nutrient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NutrientServiceDto {

    private Integer kCal;
    private Double carbohydrate;
    private Double protein;
    private Double fat;

    public static NutrientServiceDto fromEntity(Nutrient nutrient) {
        return NutrientServiceDto.builder()
            .kCal(nutrient.getKCal())
            .carbohydrate(nutrient.getCarbohydrate())
            .protein(nutrient.getProtein())
            .fat(nutrient.getFat())
            .build();
    }
}
