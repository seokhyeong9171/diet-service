package com.health.mealservice.service.impl;

import com.health.mealservice.dto.FoodServiceDto;
import com.health.elasticservice.dto.FoodElasticDto;
import com.health.elasticservice.repository.FoodElasticRepository;
import com.health.mealservice.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {

  private final FoodElasticRepository foodElasticRepository;

  @Override
  public Page<FoodServiceDto> searchFood(String searchName, Pageable pageable) {

    return foodElasticRepository.findByFoodNameContaining(searchName, pageable)
        .map(FoodServiceDto::fromElasticDto);
  }
}
