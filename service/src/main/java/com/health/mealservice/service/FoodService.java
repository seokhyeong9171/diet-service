package com.health.mealservice.service;

import com.health.mealservice.dto.FoodServiceDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FoodService {

  Page<FoodServiceDto> searchFood(String searchName, Pageable pageable);
}
