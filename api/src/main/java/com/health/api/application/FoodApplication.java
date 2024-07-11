package com.health.api.application;

import com.health.mealservice.dto.FoodServiceDto;
import com.health.mealservice.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FoodApplication {

  private final FoodService foodService;

  public Page<FoodServiceDto> searchFood(String searchName, Pageable pageable) {
    return foodService.searchFood(searchName, pageable);
  }
}
