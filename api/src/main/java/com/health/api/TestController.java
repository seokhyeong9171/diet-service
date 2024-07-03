package com.health.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.health.domain.dto.IntakeDomainDto;
import com.health.mealservice.service.FoodDataService;
import com.health.userservice.service.UserInfoService;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TestController {

  private final FoodDataService foodDataService;
  private final UserInfoService userInfoService;

  @GetMapping("/test")
  public String test() throws JsonProcessingException {

//    IntakeDomainDto dto = userInfoService.getIntakeInfo(
//        "naver_s6l2d3WzMpsNC5LDfilFIYmqepWMAvwEa5THTVL6kEQ");

//    log.info(dto.toString());
    IntakeDomainDto dto = userInfoService.getIntakeInfo("naver_s6l2d3WzMpsNC5LDfilFIYmqepWMAvwEa5THTVL6kEQ",
        LocalDate.now());
    log.info("{}", dto.toString());

    return dto.toString();
  }

  // 공공데이터 open api 테스트용
  @GetMapping("/food-openapi-test")
  public ResponseEntity<?> foodOpenApiTest() {
    foodDataService.getAndSaveFoodData();
    return ResponseEntity.ok(null);
  }

}
