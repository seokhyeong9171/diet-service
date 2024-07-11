package com.health.service.mealservice.service;

import com.health.service.mealservice.dto.MealServiceDto;
import com.health.service.mealservice.form.MealServiceForm;
import java.time.LocalDate;
import java.util.List;

public interface MealService {

  MealServiceDto createMeal(String authId, LocalDate dailyMealDt, MealServiceForm serviceForm);

  MealServiceDto getMealInfo(String authId, LocalDate dailyMealDt, Long mealId);

  List<MealServiceDto> getMealList(String authId, LocalDate dailyMealDt);

  Long deleteMeal(String authId, LocalDate dailyMealDt, Long mealId);
}
