package com.health.domain.entity;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Embeddable
public class Nutrient {

    private Integer kCal;
    private Double carbohydrate;
    private Double protein;
    private Double fat;
}
