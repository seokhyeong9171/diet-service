package com.health.domain.dto;

import com.health.domain.type.ConsumeAmount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConsumeFoodDomainDto {

    private Long id;

    private MealDomainDto meal;

    private ConsumeAmount consumeAmount;

    private FoodDomainDto food;
}
