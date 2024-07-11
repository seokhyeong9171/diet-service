package com.health.mealservice.response;

import com.health.mealservice.dto.FoodServiceDto;
import com.health.mealservice.dto.NutrientServiceDto;
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

    public static FoodResponse fromDomainDto(FoodServiceDto foodServiceDto) {
        return FoodResponse.builder()
            .foodCode(foodServiceDto.getFoodCode())
            .foodName(foodServiceDto.getFoodName())
            .foodAmount(foodServiceDto.getFoodAmount())
            .nutrient(Nutrient.fromDomainDto(foodServiceDto.getNutrientDomainDto()))
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
