package com.health.api.application;

import com.health.api.form.MealForm;
import com.health.mealservice.dto.MealServiceDto;
import com.health.mealservice.service.MealService;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MealApplication {

  private final MealService mealService;

  public MealServiceDto createMeal(String authId, LocalDate dailyMealDt, MealForm mealForm) {

    return mealService.createMeal(authId, dailyMealDt, mealForm.toDomainForm());
  }

  public MealServiceDto getMealInfo(String authId, LocalDate dailyMealDt, Long mealId) {
    return mealService.getMealInfo(authId, dailyMealDt, mealId);
  }

  public List<MealServiceDto> getMealList(String authId, LocalDate dailyMealDt) {
    return mealService.getMealList(authId, dailyMealDt);
  }

  public Long deleteMeal(String authId, LocalDate dailyMealDt, Long mealId) {
    return mealService.deleteMeal(authId, dailyMealDt, mealId);
  }
}
