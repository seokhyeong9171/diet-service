package com.health.api.application;

import com.health.api.form.FoodConsumeForm;
import com.health.service.mealservice.dto.ConsumeFoodServiceDto;
import com.health.service.mealservice.service.ConsumeFoodService;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConsumeFoodApplication {

  private final ConsumeFoodService consumeFoodService;

  public ConsumeFoodServiceDto addFoodToMeal
      (String authId, LocalDate dailyMealDt, Long mealId, FoodConsumeForm foodConsumeForm) {

    return consumeFoodService.addFoodToMeal(authId, dailyMealDt, mealId, foodConsumeForm.toDomainForm());
  }

  public Long deleteConsumeFood
      (String authId, LocalDate dailyMealDt, Long mealId, Long consumeFoodId) {

    return consumeFoodService.deleteConsumeFood(authId, dailyMealDt, mealId, consumeFoodId);
  }
}
