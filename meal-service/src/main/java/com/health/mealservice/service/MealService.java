package com.health.mealservice.service;

import com.health.domain.dto.MealDomainDto;
import com.health.domain.form.MealDomainForm;
import java.time.LocalDate;

public interface MealService {

  MealDomainDto createMeal(String authId, LocalDate dailyMealDt, MealDomainForm domainForm);
}
