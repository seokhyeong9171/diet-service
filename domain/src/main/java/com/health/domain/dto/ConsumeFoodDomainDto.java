package com.health.domain.dto;

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
public class ConsumeFoodDomainDto {

    private Long id;

    private MealEntity meal;

    private ConsumeAmount consumeAmount;

    private FoodEntity food;

    private NutrientDomainDto nutrientDomainDto;

    public static ConsumeFoodDomainDto fromEntity(ConsumeFoodEntity consumeFood) {
        return ConsumeFoodDomainDto.builder()
            .meal(consumeFood.getMeal())
            .consumeAmount(consumeFood.getConsumeAmount())
            .food(consumeFood.getFood())
            .nutrientDomainDto(NutrientDomainDto.fromEntity(consumeFood.getNutrient()))
            .build();
    }
}
