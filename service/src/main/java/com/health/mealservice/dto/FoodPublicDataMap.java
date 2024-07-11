package com.health.mealservice.dto;

import static com.health.mealservice.type.FoodPublicDataConst.CARBOHYDRATE;
import static com.health.mealservice.type.FoodPublicDataConst.CODE;
import static com.health.mealservice.type.FoodPublicDataConst.FAT;
import static com.health.mealservice.type.FoodPublicDataConst.KCAL;
import static com.health.mealservice.type.FoodPublicDataConst.NAME;
import static com.health.mealservice.type.FoodPublicDataConst.PROTEIN;
import static com.health.mealservice.type.FoodPublicDataConst.WEIGHT;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FoodPublicDataMap {

  private Body body;


  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  public static class Body {

      private Integer pageNo;
      private Integer totalCount;
      private Integer numOfRows;

      private List<Item> items = new ArrayList<>();
  }

  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  public static class Item {

    @JsonProperty(value = CODE)
    private String foodCode;

    @JsonProperty(value = NAME)
    private String name;

    @JsonProperty(value = WEIGHT)
    private String weight;

    @JsonProperty(value = KCAL)
    private String kCal;

    @JsonProperty(value = PROTEIN)
    private String protein;

    @JsonProperty(value = FAT)
    private String fat;

    @JsonProperty(value = CARBOHYDRATE)
    private String carbohydrate;

  }

}
