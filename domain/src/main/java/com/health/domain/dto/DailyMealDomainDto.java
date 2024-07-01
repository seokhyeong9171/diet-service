package com.health.domain.dto;

import com.health.domain.entity.DailyMealEntity;
import com.health.domain.entity.Nutrient;
import com.health.domain.entity.UserEntity;
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
public class DailyMealDomainDto {

    private Long id;

    private LocalDate dailyMealDt;

    private NutrientDomainDto nutrientDomainDto;

    private UserEntity user;

  public static DailyMealDomainDto fromEntity(DailyMealEntity dailyMeal) {
    return DailyMealDomainDto.builder()
        .id(dailyMeal.getId())
        .dailyMealDt(dailyMeal.getDailyMealDt())
        .nutrientDomainDto(NutrientDomainDto.fromEntity(dailyMeal.getNutrient()))
        .build();
  }
}
