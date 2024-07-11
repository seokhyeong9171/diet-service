package com.health.service.mealservice.dto;

import com.health.domain.entity.ConsumeFoodEntity;
import com.health.domain.entity.FoodEntity;
import com.health.domain.entity.MealEntity;
import com.health.domain.type.ConsumeAmount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConsumeFoodServiceDto {

    private Long id;

    private MealEntity meal;

    private ConsumeAmount consumeAmount;

    private FoodEntity food;

    private NutrientServiceDto nutrientServiceDto;

    public static ConsumeFoodServiceDto fromEntity(ConsumeFoodEntity consumeFood) {
        return ConsumeFoodServiceDto.builder()
            .meal(consumeFood.getMeal())
            .consumeAmount(consumeFood.getConsumeAmount())
            .food(consumeFood.getFood())
            .nutrientServiceDto(NutrientServiceDto.fromEntity(consumeFood.getNutrient()))
            .build();
    }
}
