package com.health.service.mealservice.dto;

import com.health.elasticservice.dto.FoodElasticDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FoodServiceDto {

    private String foodCode;

    private String foodName;

    private Integer foodAmount;

    private NutrientServiceDto nutrientDomainDto;

    public static FoodServiceDto fromElasticDto(FoodElasticDto foodElasticDto) {
        return FoodServiceDto.builder()
            .foodCode(foodElasticDto.getId())
            .foodName(foodElasticDto.getFoodName())
            .foodAmount(foodElasticDto.getFoodAmount())
            .nutrientDomainDto(NutrientServiceDto.fromEntity(foodElasticDto.getNutrient()))
            .build();
    }
}
