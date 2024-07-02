package com.health.mealservice.service;

import com.health.domain.dto.ConsumeFoodDomainDto;
import com.health.domain.form.FoodDomainForm;
import java.time.LocalDate;

public interface ConsumeFoodService {

  ConsumeFoodDomainDto addFoodToMeal
      (String authId, LocalDate dailyMealDt, Long mealId, FoodDomainForm foodDomainForm);

  Long deleteConsumeFood(String authId, LocalDate dailyMealDt, Long mealId, Long consumeFoodId);
}
