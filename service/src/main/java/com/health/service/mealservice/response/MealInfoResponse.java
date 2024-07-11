package com.health.service.mealservice.response;

import com.health.service.mealservice.dto.MealServiceDto;
import com.health.service.mealservice.dto.NutrientServiceDto;
import com.health.domain.type.MealType;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MealInfoResponse {

    private Long id;

    private MealType mealType;

    private LocalDate mealDt;

    private Nutrient nutrient;

    public static MealInfoResponse fromDto(MealServiceDto domainDto) {
        return MealInfoResponse.builder()
            .id(domainDto.getId())
            .mealType(domainDto.getMealType())
            .mealDt(domainDto.getMealDt())
            .nutrient(Nutrient.fromDomainDto(domainDto.getNutrient()))
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
