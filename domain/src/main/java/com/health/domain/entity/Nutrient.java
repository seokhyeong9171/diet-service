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

    public static Nutrient createNew() {
        return Nutrient.builder().kCal(0).carbohydrate(0.0).protein(0.0).fat(0.0).build();
    }

    public void addNutrient(Nutrient nutrient) {
        this.kCal += nutrient.getKCal();
        this.carbohydrate += nutrient.getCarbohydrate();
        this.protein += nutrient.getProtein();
        this.fat += nutrient.getFat();
    }

    public void minusNutrient(Nutrient nutrient) {
        this.kCal -= nutrient.getKCal();
        this.carbohydrate -= nutrient.getCarbohydrate();
        this.protein -= nutrient.getProtein();
        this.fat -= nutrient.getFat();
    }
}
