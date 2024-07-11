package com.health.service.mealservice.service;

import com.health.service.mealservice.dto.DailyMealServiceDto;
import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DailyMealService {

  Page<DailyMealServiceDto> getDailyMealList(String authId, Pageable pageable);

  DailyMealServiceDto createDailyMeal(String authId, LocalDate dailyMealDt);

  LocalDate deleteDailyMeal(String authId, LocalDate dailyMealDt);
}
