package com.health.mealservice.service;

import com.health.mealservice.dto.MealServiceDto;
import com.health.mealservice.form.MealServiceForm;
import java.time.LocalDate;
import java.util.List;

public interface MealService {

  MealServiceDto createMeal(String authId, LocalDate dailyMealDt, MealServiceForm serviceForm);

  MealServiceDto getMealInfo(String authId, LocalDate dailyMealDt, Long mealId);

  List<MealServiceDto> getMealList(String authId, LocalDate dailyMealDt);

  Long deleteMeal(String authId, LocalDate dailyMealDt, Long mealId);
}
