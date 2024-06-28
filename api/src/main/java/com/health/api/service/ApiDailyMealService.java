package com.health.api.service;

import com.health.domain.dto.DailyMealDomainDto;
import com.health.mealservice.service.DailyMealService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApiDailyMealService {

  private final DailyMealService dailyMealService;

  public Page<DailyMealDomainDto> getDailyMealList(String authId, Pageable pageable) {
    return dailyMealService.getDailyMealList(authId, pageable);
  }
}
