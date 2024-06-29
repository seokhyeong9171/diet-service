package com.health.domain.response;

import com.health.domain.dto.ConsumeFoodDomainDto;
import com.health.domain.dto.DailyMealDomainDto;
import com.health.domain.dto.MealDomainDto;
import com.health.domain.dto.NutrientDomainDto;
import com.health.domain.entity.MealEntity;
import com.health.domain.type.MealType;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MealResponse {

    private Long id;

    private MealType mealType;

    private Nutrient nutrient;

    private LocalDate mealDt;

    public static MealResponse fromDto(MealDomainDto domainDto) {
        return MealResponse.builder()
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
