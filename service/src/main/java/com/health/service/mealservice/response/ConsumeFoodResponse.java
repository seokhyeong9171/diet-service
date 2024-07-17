package com.health.service.mealservice.response;

import com.health.domain.type.ConsumeAmount;
import com.health.service.mealservice.dto.ConsumeFoodServiceDto;
import com.health.service.mealservice.dto.NutrientServiceDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConsumeFoodResponse {

  private Long id;

  private String foodName;
  private ConsumeAmount consumeAmount;

  private Nutrient nutrient;

  public static ConsumeFoodResponse fromDomainDto(ConsumeFoodServiceDto consumeFood) {
    return ConsumeFoodResponse.builder()
        .id(consumeFood.getId())
        .foodName(consumeFood.getFood().getFoodName())
        .consumeAmount(consumeFood.getConsumeAmount())
        .nutrient(Nutrient.fromDomainDto(consumeFood.getNutrientServiceDto()))
        .build();
  }



  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  private static class Nutrient {
    private Integer kCal;
    private Double carbohydrate;
    private Double protein;
    private Double fat;

    public static Nutrient fromDomainDto(NutrientServiceDto domainDto) {
      return Nutrient.builder()
          .kCal(domainDto.getKCal())
          .carbohydrate(domainDto.getCarbohydrate())
          .protein(domainDto.getProtein())
          .fat(domainDto.getFat())
          .build();
    }
  }

}
