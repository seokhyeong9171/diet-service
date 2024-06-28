package com.health.mealservice.service;

import com.health.domain.dto.MealDomainDto;
import com.health.domain.form.MealDomainForm;

public interface MealService {

  MealDomainDto createMeal(String authId, MealDomainForm domainForm);
}
