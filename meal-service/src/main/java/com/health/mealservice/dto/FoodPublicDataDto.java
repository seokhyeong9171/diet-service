package com.health.mealservice.dto;

import com.health.domain.entity.FoodEntity;
import com.health.domain.entity.Nutrient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class FoodPublicDataDto {

  private String foodCode;

  private String foodName;

  private Integer foodAmount;

  private Integer kCal;

  private Integer carbohydrate;

  private Integer protein;

  private Integer fat;

  public FoodEntity toEntity() {
    return FoodEntity.builder()
        .foodCode(foodCode)
        .foodName(foodName)
        .foodAmount(foodAmount)
        .nutrient(toNutrientEntity())
        .build();
  }

  private Nutrient toNutrientEntity() {
    return Nutrient.builder()
        .kCal(kCal)
        .carbohydrate(carbohydrate.doubleValue())
        .protein(protein.doubleValue())
        .fat(fat.doubleValue())
        .build();
  }

}
