package com.health.domain.response;

import com.health.domain.dto.MealDomainDto;
import com.health.domain.dto.NutrientDomainDto;
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

    public static MealInfoResponse fromDto(MealDomainDto domainDto) {
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
