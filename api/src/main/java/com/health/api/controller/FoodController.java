package com.health.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FoodController {

  //TODO
  // food 조회 기능
  public ResponseEntity<?> searchFood(String searchCond) {

    return ResponseEntity.ok(null);
  }

}
