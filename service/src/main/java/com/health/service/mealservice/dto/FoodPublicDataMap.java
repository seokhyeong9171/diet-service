package com.health.service.mealservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.health.service.mealservice.type.FoodPublicDataConst;
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

    @JsonProperty(value = FoodPublicDataConst.CODE)
    private String foodCode;

    @JsonProperty(value = FoodPublicDataConst.NAME)
    private String name;

    @JsonProperty(value = FoodPublicDataConst.WEIGHT)
    private String weight;

    @JsonProperty(value = FoodPublicDataConst.KCAL)
    private String kCal;

    @JsonProperty(value = FoodPublicDataConst.PROTEIN)
    private String protein;

    @JsonProperty(value = FoodPublicDataConst.FAT)
    private String fat;

    @JsonProperty(value = FoodPublicDataConst.CARBOHYDRATE)
    private String carbohydrate;

  }

}
