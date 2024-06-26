package com.health.mealservice.service;

import com.health.domain.dto.MealDomainDto;
import com.health.domain.form.MealDomainForm;
import java.time.LocalDate;
import java.util.List;

public interface MealService {

  MealDomainDto createMeal(String authId, LocalDate dailyMealDt, MealDomainForm domainForm);

  MealDomainDto getMealInfo(String authId, LocalDate dailyMealDt, Long mealId);

  List<MealDomainDto> getMealList(String authId, LocalDate dailyMealDt);

  Long deleteMeal(String authId, LocalDate dailyMealDt, Long mealId);
}
