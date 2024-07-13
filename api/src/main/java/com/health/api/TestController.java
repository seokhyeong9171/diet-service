package com.health.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.health.service.mealservice.service.FoodDataService;
import com.health.service.meetingservice.component.CalendarComponent;
import com.health.service.meetingservice.dto.CalendarDto.Request;
import com.health.service.meetingservice.dto.CalendarDto.Response;
import com.health.service.userservice.dto.IntakeServiceDto;
import com.health.service.userservice.service.UserInfoService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TestController {

  private final FoodDataService foodDataService;
  private final UserInfoService userInfoService;
  private final CalendarComponent calendarComponent;

  @GetMapping("/test")
  public String test() throws JsonProcessingException {

//    IntakeRedisDto dto = userInfoService.getIntakeInfo(
//        "naver_s6l2d3WzMpsNC5LDfilFIYmqepWMAvwEa5THTVL6kEQ");

//    log.info(dto.toString());
    IntakeServiceDto dto = userInfoService.getIntakeInfo("naver_s6l2d3WzMpsNC5LDfilFIYmqepWMAvwEa5THTVL6kEQ",
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

  @PostMapping("/calender-test")
  public void calender() {

    LocalDateTime localDateTime = LocalDateTime.of(2024, Month.of(7), 24, 11, 11, 11);

    Request build = Request.builder().title("test").content("content").meetingDt(localDateTime)
        .address("address").build();

    Response response = calendarComponent.addCalendar(
        "AAAAN0zO5txyPmfg34gbQ37RKIo8BX-YyQXmY0ee8FiuN2hbPNS6W2vrGbPJc_EUeYuMLIwki-y3xf1BWio48hJEN9g",
        build
    );
    log.info(response.toString());

  }

}
