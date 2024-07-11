package com.health.service.mealservice.service.impl;

import com.health.elasticservice.service.FoodElasticService;
import com.health.service.mealservice.dto.FoodServiceDto;
import com.health.service.mealservice.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {

  private final FoodElasticService foodElasticService;

  @Override
  public Page<FoodServiceDto> searchFood(String searchName, Pageable pageable) {

    return foodElasticService.findByContainingName(searchName, pageable)
        .map(FoodServiceDto::fromElasticDto);
  }
}
