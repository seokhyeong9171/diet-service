package com.health.service.mealservice.service;

import com.health.service.mealservice.dto.FoodServiceDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FoodService {

  Page<FoodServiceDto> searchFood(String searchName, Pageable pageable);
}
