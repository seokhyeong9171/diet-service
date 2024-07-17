package com.health.api.controller;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;
import static org.springframework.http.HttpStatus.CREATED;

import com.health.api.application.DailyMealApplication;
import com.health.api.model.SuccessResponse;
import com.health.security.authentication.AuthValidatorComponent;
import com.health.service.mealservice.dto.DailyMealServiceDto;
import com.health.service.mealservice.response.DailyMealResponse;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dailymeals")
@RequiredArgsConstructor
public class DailyMealController {

  private final AuthValidatorComponent authValidatorComponent;
  private final DailyMealApplication dailyMealApplication;

  @GetMapping
  public ResponseEntity<?> getDailyMealList(
      @CookieValue(name = "Authorization") String jwt, Pageable pageable
  ) {

    String authId = authValidatorComponent.validateAuthId(jwt);

    Page<DailyMealServiceDto> domainDtoList =
        dailyMealApplication.getDailyMealList(authId, pageable);

    return ResponseEntity.ok(
        SuccessResponse.of(domainDtoList.map(DailyMealResponse::fromDomainDto))
    );
  }

  @PostMapping("/{dailyMealDt}")
  public ResponseEntity<?> createDailyMeal(
      @CookieValue(name = "Authorization") String jwt,
      @PathVariable @DateTimeFormat(iso = DATE) LocalDate dailyMealDt
  ) {

    String authId = authValidatorComponent.validateAuthId(jwt);

    DailyMealServiceDto dailyMeal = dailyMealApplication.createDailyMeal(authId, dailyMealDt);

    return ResponseEntity.status(CREATED)
        .body(SuccessResponse.of(DailyMealResponse.fromDomainDto(dailyMeal)));
  }

  @DeleteMapping("/{dailyMealDt}")
  public ResponseEntity<?> deleteDailyMeal(
      @CookieValue(name = "Authorization") String jwt,
      @PathVariable @DateTimeFormat(iso = DATE) LocalDate dailyMealDt
  ) {

    String authId = authValidatorComponent.validateAuthId(jwt);

    LocalDate deletedDailyMealDt = dailyMealApplication.deleteDailyMeal(authId, dailyMealDt);

    return ResponseEntity.ok(SuccessResponse.of(deletedDailyMealDt));
  }

}
