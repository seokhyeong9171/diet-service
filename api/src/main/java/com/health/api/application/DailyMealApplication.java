package com.health.api.application;

import com.health.mealservice.dto.DailyMealServiceDto;
import com.health.mealservice.service.DailyMealService;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DailyMealApplication {

  private final DailyMealService dailyMealService;

  public Page<DailyMealServiceDto> getDailyMealList(String authId, Pageable pageable) {
    return dailyMealService.getDailyMealList(authId, pageable);
  }

  public DailyMealServiceDto createDailyMeal(String authId, LocalDate dailyMealDt) {
    return dailyMealService.createDailyMeal(authId, dailyMealDt);
  }

  public LocalDate deleteDailyMeal(String authId, LocalDate dailyMealDt) {
    return dailyMealService.deleteDailyMeal(authId, dailyMealDt);
  }
}
