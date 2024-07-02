package com.health.domain.response;

import com.health.domain.dto.FoodDomainDto;
import com.health.domain.dto.NutrientDomainDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FoodResponse {

    private String foodCode;

    private String foodName;

    private Integer foodAmount;

    private Nutrient nutrient;

    public static FoodResponse fromDomainDto(FoodDomainDto foodDomainDto) {
        return FoodResponse.builder()
            .foodCode(foodDomainDto.getFoodCode())
            .foodName(foodDomainDto.getFoodName())
            .foodAmount(foodDomainDto.getFoodAmount())
            .nutrient(Nutrient.fromDomainDto(foodDomainDto.getNutrientDomainDto()))
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
