package com.health.domain.dto;

import com.health.domain.entity.Nutrient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NutrientDomainDto {

    private Integer kCal;
    private Double carbohydrate;
    private Double protein;
    private Double fat;

    public static NutrientDomainDto fromEntity(Nutrient nutrient) {
        return NutrientDomainDto.builder()
            .kCal(nutrient.getKCal())
            .carbohydrate(nutrient.getCarbohydrate())
            .protein(nutrient.getProtein())
            .fat(nutrient.getFat())
            .build();
    }
}
