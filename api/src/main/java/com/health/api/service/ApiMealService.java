package com.health.api.service;

import com.health.api.form.MealForm;
import com.health.domain.dto.MealDomainDto;
import com.health.mealservice.service.MealService;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApiMealService {

  private final MealService mealService;

  public MealDomainDto createMeal(String authId, LocalDate dailyMealDt, MealForm mealForm) {

    return mealService.createMeal(authId, dailyMealDt, mealForm.toDomainForm());
  }

  public MealDomainDto getMealInfo(String authId, LocalDate dailyMealDt, Long mealId) {
    return mealService.getMealInfo(authId, dailyMealDt, mealId);
  }

  public List<MealDomainDto> getMealList(String authId, LocalDate dailyMealDt) {
    return mealService.getMealList(authId, dailyMealDt);
  }

  public Long deleteMeal(String authId, LocalDate dailyMealDt, Long mealId) {
    return mealService.deleteMeal(authId, dailyMealDt, mealId);
  }
}
