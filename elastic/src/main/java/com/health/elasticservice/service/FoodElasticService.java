package com.health.elasticservice.service;

import com.health.elasticservice.dto.FoodElasticDto;
import com.health.elasticservice.repository.FoodElasticRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FoodElasticService {

  private final FoodElasticRepository foodElasticRepository;

  public Page<FoodElasticDto> findByContainingName(String nameCond, Pageable pageable) {
    return foodElasticRepository.findByFoodNameContaining(nameCond, pageable);
  }

  public Iterable<FoodElasticDto> saveAll(List<FoodElasticDto> foodElasticDtoList) {
    return foodElasticRepository.saveAll(foodElasticDtoList);
  }

}
