package com.health.service.meetingservice.client;

import com.health.service.meetingservice.dto.CalendarDto;
import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "naverCalendarClient", url = "https://openapi.naver.com")
public interface NaverCalendarClient {

  @PostMapping(value = "/calendar/createSchedule.json",
      consumes = "application/x-www-form-urlencoded", produces = "application/json")
  CalendarDto.Response addCalendar(
      @RequestHeader("Authorization") String header, @RequestBody Map<String, ?> form
  );

}
