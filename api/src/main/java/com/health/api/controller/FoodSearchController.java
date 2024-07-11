package com.health.api.controller;

import com.health.api.application.FoodApplication;
import com.health.api.model.SuccessResponse;
import com.health.domain.dto.FoodDomainDto;
import com.health.domain.response.FoodResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/foods")
@RequiredArgsConstructor
public class FoodSearchController {

  private final FoodApplication foodApplication;

  @GetMapping
  public ResponseEntity<?> searchFood(@RequestParam String searchName, Pageable pageable) {

    Page<FoodDomainDto> foodDtoList = foodApplication.searchFood(searchName, pageable);

    return ResponseEntity.ok(SuccessResponse.of(foodDtoList.map(FoodResponse::fromDomainDto)));
  }

}
