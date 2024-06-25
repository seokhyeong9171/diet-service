package com.health.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

  @GetMapping("/test")
  public String test() {
    return "test";
  }

  // 공공데이터 open api 테스트용
  // TODO
  //  추후 스케쥴러로 실행 예정
  @GetMapping("/food-openapi-test")
  public ResponseEntity<?> foodOpenApiTest() {
    return ResponseEntity.ok(null);
  }

}
