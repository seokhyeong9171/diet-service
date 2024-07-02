package com.health.api.controller;

import com.health.api.service.FoodApplication;
import com.health.common.model.SuccessResponse;
import com.health.domain.dto.FoodDomainDto;
import com.health.domain.response.FoodResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
  public ResponseEntity<?> searchFood(@RequestParam String searchName, @RequestParam int page) {

    Page<FoodDomainDto> foodDtoList = foodApplication.searchFood(searchName, page);

    return ResponseEntity.ok(SuccessResponse.of(foodDtoList.map(FoodResponse::fromDomainDto)));
  }

}
