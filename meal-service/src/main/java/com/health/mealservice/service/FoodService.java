package com.health.mealservice.service;

import com.health.domain.dto.FoodDomainDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FoodService {

  Page<FoodDomainDto> searchFood(String searchName, Pageable pageable);
}
