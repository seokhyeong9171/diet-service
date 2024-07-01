package com.health.api.controller;

import static org.springframework.format.annotation.DateTimeFormat.ISO.*;

import com.health.api.service.ApiDailyMealService;
import com.health.common.model.SuccessResponse;
import com.health.domain.dto.DailyMealDomainDto;
import com.health.domain.response.DailyMealResponse;
import com.health.security.authentication.AuthValidatorComponent;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/{authId}/dailymeal")
@RequiredArgsConstructor
public class DailyMealController {

  private final AuthValidatorComponent authValidatorComponent;
  private final ApiDailyMealService apiDailyMealService;

  @GetMapping
  public ResponseEntity<?> getDailyMealList(@PathVariable String authId, Pageable pageable) {

    authValidatorComponent.validateAuthId(authId);

    Page<DailyMealDomainDto> domainDtoList =
        apiDailyMealService.getDailyMealList(authId, pageable);

    return ResponseEntity.ok(
        SuccessResponse.of(domainDtoList.map(DailyMealResponse::fromDomainDto))
    );
  }

  @PostMapping("/{dailyMealDt}")
  public ResponseEntity<?> createDailyMeal(
      @PathVariable String authId,
      @PathVariable @DateTimeFormat(iso = DATE) LocalDate dailyMealDt
  ) {

    authValidatorComponent.validateAuthId(authId);

    DailyMealDomainDto dailyMeal = apiDailyMealService.createDailyMeal(authId, dailyMealDt);

    return ResponseEntity.ok(SuccessResponse.of(DailyMealResponse.fromDomainDto(dailyMeal)));
  }

  @DeleteMapping("/{dailyMealDt}")
  public ResponseEntity<?> deleteDailyMeal(
      @PathVariable String authId,
      @PathVariable @DateTimeFormat(iso = DATE) LocalDate dailyMealDt
  ) {

    authValidatorComponent.validateAuthId(authId);

    LocalDate deletedDailyMealDt = apiDailyMealService.deleteDailyMeal(authId, dailyMealDt);

    return ResponseEntity.ok(SuccessResponse.of(deletedDailyMealDt));
  }

}
