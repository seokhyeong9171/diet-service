package com.health.domain.response;

import com.health.domain.dto.DailyMealDomainDto;
import com.health.domain.dto.NutrientDomainDto;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DailyMealResponse {

  private LocalDate dailyMealDt;
  private Nutrient nutrient;

  public static DailyMealResponse fromDomainDto(DailyMealDomainDto domainDto) {
    return DailyMealResponse.builder()
        .dailyMealDt(domainDto.getDailyMealDt())
        .nutrient(Nutrient.fromDomainDto(domainDto.getNutrientDomainDto()))
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
