package com.health.domain.dto;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
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
}
