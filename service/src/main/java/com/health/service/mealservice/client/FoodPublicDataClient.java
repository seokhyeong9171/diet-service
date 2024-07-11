package com.health.service.mealservice.client;

import com.health.service.mealservice.dto.FoodPublicDataMap;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(name = "open-api", url = "${public-data.url}")
public interface FoodPublicDataClient {

  @GetMapping("")
  ResponseEntity<FoodPublicDataMap> getFoodList(
      @RequestParam String serviceKey,
      @RequestParam Integer pageNo, @RequestParam Integer numOfRows,
      @RequestParam String type
  );

}
