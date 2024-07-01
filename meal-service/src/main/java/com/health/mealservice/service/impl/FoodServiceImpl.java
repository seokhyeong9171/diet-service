package com.health.mealservice.service.impl;

import com.health.domain.dto.FoodDomainDto;
import com.health.domain.entity.FoodElasticEntity;
import com.health.domain.repository.elastic.FoodElasticRepository;
import com.health.mealservice.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {

  private final FoodElasticRepository foodElasticRepository;

  @Override
  public Page<FoodDomainDto> searchFood(String searchName, int page) {

    Pageable pageable = PageRequest.of(page, 10);

    return foodElasticRepository.findByFoodNameContaining(searchName, pageable)
        .map(FoodElasticEntity::toDomainDto);
  }
}
