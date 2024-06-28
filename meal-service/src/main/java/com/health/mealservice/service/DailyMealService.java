package com.health.mealservice.service;

import com.health.domain.dto.DailyMealDomainDto;
import com.health.domain.entity.DailyMealEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DailyMealService {

  Page<DailyMealDomainDto> getDailyMealList(String authId, Pageable pageable);
}
