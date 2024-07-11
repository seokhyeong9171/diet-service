package com.health.service.mealservice.dto;

import com.health.domain.entity.DailyMealEntity;
import com.health.domain.entity.UserEntity;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DailyMealServiceDto {

    private Long id;

    private LocalDate dailyMealDt;

    private NutrientServiceDto nutrientServiceDto;

    private UserEntity user;

  public static DailyMealServiceDto fromEntity(DailyMealEntity dailyMeal) {
    return DailyMealServiceDto.builder()
        .id(dailyMeal.getId())
        .dailyMealDt(dailyMeal.getDailyMealDt())
        .nutrientServiceDto(NutrientServiceDto.fromEntity(dailyMeal.getNutrient()))
        .build();
  }
}
