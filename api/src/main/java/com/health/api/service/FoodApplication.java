package com.health.api.service;

import com.health.domain.dto.FoodDomainDto;
import com.health.mealservice.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FoodApplication {

  private final FoodService foodService;

  public Page<FoodDomainDto> searchFood(String searchName, int page) {
    return foodService.searchFood(searchName, page);
  }
}
