package com.health.api.controller;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;
import static org.springframework.http.HttpStatus.CREATED;

import com.health.api.form.FoodConsumeForm;
import com.health.api.service.ApiConsumeFoodService;
import com.health.common.model.SuccessResponse;
import com.health.domain.dto.ConsumeFoodDomainDto;
import com.health.domain.response.ConsumeFoodResponse;
import com.health.security.authentication.AuthValidatorComponent;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/{authId}/dailymeal/{dailyMealDt}/meal/{mealId}")
@RequiredArgsConstructor
public class ConsumeFoodController {

  private final AuthValidatorComponent authValidatorComponent;
  private final ApiConsumeFoodService apiConsumeFoodService;


  @PostMapping
  public ResponseEntity<?> addFoodToMeal(
      @PathVariable String authId,
      @PathVariable @DateTimeFormat(iso = DATE) LocalDate dailyMealDt,
      @PathVariable Long mealId,
      @RequestBody @Validated FoodConsumeForm foodConsumeForm
  ) {

    authValidatorComponent.validateAuthId(authId);

    ConsumeFoodDomainDto consumeFoodDomainDto =
        apiConsumeFoodService.addFoodToMeal(authId, dailyMealDt, mealId, foodConsumeForm);

    return ResponseEntity.status(CREATED).body(
        SuccessResponse.of(ConsumeFoodResponse.fromDomainDto(consumeFoodDomainDto))
    );
  }

  @DeleteMapping("/{consumeFoodId}")
  public ResponseEntity<?> deleteConsumeFood(
      @PathVariable String authId,
      @PathVariable @DateTimeFormat(iso = DATE) LocalDate dailyMealDt,
      @PathVariable Long mealId,
      @PathVariable Long consumeFoodId
      ) {

    authValidatorComponent.validateAuthId(authId);

    Long deletedConsumeFoodId =
        apiConsumeFoodService.deleteConsumeFood(authId, dailyMealDt, mealId, consumeFoodId);

    return ResponseEntity.ok(SuccessResponse.of(deletedConsumeFoodId));
  }

}
