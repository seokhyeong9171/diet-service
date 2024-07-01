package com.health.api.controller;

import static org.springframework.format.annotation.DateTimeFormat.ISO.*;
import static org.springframework.http.HttpStatus.*;

import com.health.api.form.MealForm;
import com.health.api.service.ApiMealService;
import com.health.common.model.SuccessResponse;
import com.health.domain.dto.MealDomainDto;
import com.health.domain.form.MealDomainForm;
import com.health.domain.response.MealResponse;
import com.health.security.authentication.AuthValidatorComponent;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/{authId}/dailymeal/{dailyMealDt}/meal")
@RequiredArgsConstructor
public class MealController {

  private final AuthValidatorComponent authValidatorComponent;
  private final ApiMealService apiMealService;

  @GetMapping
  public ResponseEntity<?> getMealList(
      @PathVariable String authId,
      @PathVariable @DateTimeFormat(iso = DATE) LocalDate dailyMealDt
  ) {

    authValidatorComponent.validateAuthId(authId);

    List<MealDomainDto> mealList = apiMealService.getMealList(authId, dailyMealDt);

    return ResponseEntity.ok(
        SuccessResponse.of(mealList.stream().map(MealResponse::fromDto))
    );
  }

  @GetMapping("/{mealId}")
  public ResponseEntity<?> getMealInfo(
      @PathVariable String authId,
      @PathVariable @DateTimeFormat(iso = DATE) LocalDate dailyMealDt,
      @PathVariable Long mealId
  ) {

    authValidatorComponent.validateAuthId(authId);

    MealDomainDto meal = apiMealService.getMealInfo(authId, dailyMealDt, mealId);

    return ResponseEntity.ok(SuccessResponse.of(MealResponse.fromDto(meal)));
  }

  @PostMapping
  public ResponseEntity<?> addMeal(
      @PathVariable String authId,
      @PathVariable @DateTimeFormat(iso = DATE) LocalDate dailyMealDt,
      @RequestBody @Validated MealForm mealForm
  ) {

    authValidatorComponent.validateAuthId(authId);

    MealDomainDto mealDomainDto =  apiMealService.createMeal(authId, dailyMealDt, mealForm);

    return ResponseEntity.status(CREATED).body(
        SuccessResponse.of(MealResponse.fromDto(mealDomainDto))
    );
  }

  @DeleteMapping("/{mealId}")
  public ResponseEntity<?> deleteMeal(
      @PathVariable String authId,
      @PathVariable @DateTimeFormat(iso = DATE) LocalDate dailyMealDt,
      @PathVariable Long mealId
  ) {

    authValidatorComponent.validateAuthId(authId);

    Long deletedMealId = apiMealService.deleteMeal(authId, dailyMealDt, mealId);

    return ResponseEntity.ok(SuccessResponse.of(deletedMealId));
  }



}
