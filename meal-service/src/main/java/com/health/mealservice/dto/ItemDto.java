package com.health.mealservice.dto;

import static com.health.mealservice.type.FoodPublicDataConst.CARBOHYDRATE;
import static com.health.mealservice.type.FoodPublicDataConst.FAT;
import static com.health.mealservice.type.FoodPublicDataConst.KCAL;
import static com.health.mealservice.type.FoodPublicDataConst.NAME;
import static com.health.mealservice.type.FoodPublicDataConst.PROTEIN;
import static com.health.mealservice.type.FoodPublicDataConst.WEIGHT;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.health.mealservice.deseriaizer.FoodPublicDataDeserializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemDto {

  private String foodCode;

  private String name;

  private Integer weight;

  private Integer kCal;

  private Integer protein;

  private Integer fat;

  private Integer carbohydrate;

}
