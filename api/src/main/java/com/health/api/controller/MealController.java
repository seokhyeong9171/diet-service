package com.health.api.controller;

import static org.springframework.format.annotation.DateTimeFormat.ISO.*;
import static org.springframework.http.HttpStatus.*;

import com.health.api.form.MealForm;
import com.health.api.service.MealApplication;
import com.health.common.model.SuccessResponse;
import com.health.domain.dto.MealDomainDto;
import com.health.domain.response.MealInfoResponse;
import com.health.security.authentication.AuthValidatorComponent;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
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
@RequestMapping("/authid/{authId}/dailymeals/{dailyMealDt}/meals")
@RequiredArgsConstructor
public class MealController {

  private final AuthValidatorComponent authValidatorComponent;
  private final MealApplication mealApplication;

  @GetMapping
  public ResponseEntity<?> getMealList(
      @PathVariable String authId,
      @PathVariable @DateTimeFormat(iso = DATE) LocalDate dailyMealDt
  ) {

    authValidatorComponent.validateAuthId(authId);

    List<MealDomainDto> mealList = mealApplication.getMealList(authId, dailyMealDt);

    return ResponseEntity.ok(
        SuccessResponse.of(mealList.stream().map(MealInfoResponse::fromDto))
    );
  }

  @GetMapping("/{mealId}")
  public ResponseEntity<?> getMealInfo(
      @PathVariable String authId,
      @PathVariable @DateTimeFormat(iso = DATE) LocalDate dailyMealDt,
      @PathVariable Long mealId
  ) {

    authValidatorComponent.validateAuthId(authId);

    MealDomainDto meal = mealApplication.getMealInfo(authId, dailyMealDt, mealId);

    return ResponseEntity.ok(SuccessResponse.of(MealInfoResponse.fromDto(meal)));
  }

  @PostMapping
  public ResponseEntity<?> addMeal(
      @PathVariable String authId,
      @PathVariable @DateTimeFormat(iso = DATE) LocalDate dailyMealDt,
      @RequestBody @Validated MealForm mealForm
  ) {

    authValidatorComponent.validateAuthId(authId);

    MealDomainDto mealDomainDto =  mealApplication.createMeal(authId, dailyMealDt, mealForm);

    return ResponseEntity.status(CREATED).body(
        SuccessResponse.of(MealInfoResponse.fromDto(mealDomainDto))
    );
  }

  @DeleteMapping("/{mealId}")
  public ResponseEntity<?> deleteMeal(
      @PathVariable String authId,
      @PathVariable @DateTimeFormat(iso = DATE) LocalDate dailyMealDt,
      @PathVariable Long mealId
  ) {

    authValidatorComponent.validateAuthId(authId);

    Long deletedMealId = mealApplication.deleteMeal(authId, dailyMealDt, mealId);

    return ResponseEntity.ok(SuccessResponse.of(deletedMealId));
  }



}
