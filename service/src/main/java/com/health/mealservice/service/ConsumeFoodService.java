package com.health.mealservice.service;

import com.health.mealservice.dto.ConsumeFoodServiceDto;
import com.health.mealservice.form.FoodServiceForm;
import java.time.LocalDate;

public interface ConsumeFoodService {

  ConsumeFoodServiceDto addFoodToMeal
      (String authId, LocalDate dailyMealDt, Long mealId, FoodServiceForm foodServiceForm);

  Long deleteConsumeFood(String authId, LocalDate dailyMealDt, Long mealId, Long consumeFoodId);
}
