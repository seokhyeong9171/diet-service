package com.health.mealservice.service;

import com.health.domain.dto.FoodDomainDto;
import org.springframework.data.domain.Page;

public interface FoodService {

  Page<FoodDomainDto> searchFood(String searchName, int page);
}
