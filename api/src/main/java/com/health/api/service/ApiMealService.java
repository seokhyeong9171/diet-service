package com.health.api.service;

import com.health.api.form.MealForm;
import com.health.domain.dto.MealDomainDto;
import com.health.mealservice.service.MealService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApiMealService {

  private final MealService mealService;

  public MealDomainDto createMeal(String authId, MealForm mealForm) {

    return mealService.createMeal(authId, mealForm.toDomainForm());
  }
}
