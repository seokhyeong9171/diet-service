package com.health.domain.response;

import com.health.domain.dto.ConsumeFoodDomainDto;
import com.health.domain.dto.NutrientDomainDto;
import com.health.domain.type.ConsumeAmount;
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

  public static ConsumeFoodResponse fromDomainDto(ConsumeFoodDomainDto consumeFood) {
    return ConsumeFoodResponse.builder()
        .id(consumeFood.getId())
        .foodName(consumeFood.getFood().getFoodName())
        .consumeAmount(consumeFood.getConsumeAmount())
        .nutrient(Nutrient.fromDomainDto(consumeFood.getNutrientDomainDto()))
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

    public static Nutrient fromDomainDto(NutrientDomainDto domainDto) {
      return Nutrient.builder()
          .kCal(domainDto.getKCal())
          .carbohydrate(domainDto.getCarbohydrate())
          .protein(domainDto.getProtein())
          .fat(domainDto.getFat())
          .build();
    }
  }

}
