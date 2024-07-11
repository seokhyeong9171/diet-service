package com.health.api.controller;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;
import static org.springframework.http.HttpStatus.CREATED;

import com.health.api.form.FoodConsumeForm;
import com.health.api.application.ConsumeFoodApplication;
import com.health.api.model.SuccessResponse;
import com.health.service.mealservice.dto.ConsumeFoodServiceDto;
import com.health.service.mealservice.response.ConsumeFoodResponse;
import com.health.security.authentication.AuthValidatorComponent;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("/authid/{authId}/dailymeals/{dailyMealDt}/meals/{mealId}")
@RequestMapping("dailymeals/{dailyMealDt}/meals/{mealId}")
@RequiredArgsConstructor
public class ConsumeFoodController {

  private final AuthValidatorComponent authValidatorComponent;
  private final ConsumeFoodApplication consumeFoodApplication;


  @PostMapping
  public ResponseEntity<?> addFoodToMeal(
//      @PathVariable String authId,
      @CookieValue(name = "Authorization") String jwt,
      @PathVariable @DateTimeFormat(iso = DATE) LocalDate dailyMealDt,
      @PathVariable Long mealId,
      @RequestBody @Validated FoodConsumeForm foodConsumeForm
  ) {

    String authId = authValidatorComponent.validateAuthId(jwt);

    ConsumeFoodServiceDto consumeFoodServiceDto =
        consumeFoodApplication.addFoodToMeal(authId, dailyMealDt, mealId, foodConsumeForm);

    return ResponseEntity.status(CREATED).body(
        SuccessResponse.of(ConsumeFoodResponse.fromDomainDto(consumeFoodServiceDto))
    );
  }

  @DeleteMapping("/{consumeFoodId}")
  public ResponseEntity<?> deleteConsumeFood(
      @CookieValue(name = "Authorization") String jwt,
      @PathVariable @DateTimeFormat(iso = DATE) LocalDate dailyMealDt,
      @PathVariable Long mealId,
      @PathVariable Long consumeFoodId
      ) {

    String authId = authValidatorComponent.validateAuthId(jwt);

    Long deletedConsumeFoodId =
        consumeFoodApplication.deleteConsumeFood(authId, dailyMealDt, mealId, consumeFoodId);

    return ResponseEntity.ok(SuccessResponse.of(deletedConsumeFoodId));
  }

}
