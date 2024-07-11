package com.health.mealservice.service;

import com.health.domain.dto.ConsumeFoodDomainDto;
import com.health.mealservice.form.FoodServiceForm;
import java.time.LocalDate;

public interface ConsumeFoodService {

  ConsumeFoodDomainDto addFoodToMeal
      (String authId, LocalDate dailyMealDt, Long mealId, FoodServiceForm foodServiceForm);

  Long deleteConsumeFood(String authId, LocalDate dailyMealDt, Long mealId, Long consumeFoodId);
}
