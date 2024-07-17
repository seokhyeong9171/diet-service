package com.health.service.meetingservice.component;

import com.health.service.meetingservice.client.NaverCalendarClient;
import com.health.service.meetingservice.dto.CalendarDto;
import com.health.service.meetingservice.dto.CalendarDto.Request;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CalendarComponent {

  private final NaverCalendarClient naverCalendarClient;

  private static final DateTimeFormatter naverCalendarTimeFormatter =
      DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss");

  public CalendarDto.Response addCalendar(String accessToken, CalendarDto.Request request) {

    String header = "Bearer " + accessToken;

    try {

      String scheduleIcalString = getScheduleIcalString(request);
      Map<String, String> form = getRequestForm(scheduleIcalString);

      return naverCalendarClient.addCalendar(header, form);

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }


  private String getScheduleIcalString(Request request) {
    String calSum = getUrlEncodedString(request.getTitle());
    String calDes = getUrlEncodedString(request.getContent());
    String calLoc = getUrlEncodedString(request.getAddress());
    String date = request.getMeetingDt().format(naverCalendarTimeFormatter);
    String curFormattedDateTime = LocalDateTime.now().format(naverCalendarTimeFormatter);
    String uid = UUID.randomUUID().toString();

    return "BEGIN:VCALENDAR\n" +
        "VERSION:2.0\n" +
        "PRODID:Naver Calendar\n" +
        "CALSCALE:GREGORIAN\n" +
        "BEGIN:VTIMEZONE\n" +
        "TZID:Asia/Seoul\n" +
        "BEGIN:STANDARD\n" +
        "DTSTART:19700101T000000\n" +
        "TZNAME:GMT+09:00\n" +
        "TZOFFSETFROM:+0900\n" +
        "TZOFFSETTO:+0900\n" +
        "END:STANDARD\n" +
        "END:VTIMEZONE\n" +
        "BEGIN:VEVENT\n" +
        "SEQUENCE:0\n" +
        "CLASS:PUBLIC\n" +
        "TRANSP:OPAQUE\n" +
        "UID:" + uid + "\n" +
        "DTSTART;TZID=Asia/Seoul:" + date + "\n" +  // 시작 일시
        "DTEND;TZID=Asia/Seoul:" + date + "\n" +    // 종료 일시
        "SUMMARY:" + calSum + " \n" +               // 일정 제목
        "DESCRIPTION:" + calDes + " \n" +           // 일정 상세 내용
        "LOCATION:" + calLoc + " \n" +              // 장소
        "CREATED:" + curFormattedDateTime + "Z\n" +         // 일정 생성시각
        "LAST-MODIFIED:" + curFormattedDateTime + "Z\n" +   // 일정 수정시각
        "DTSTAMP:" + curFormattedDateTime + "Z\n" +         // 일정 타임스탬프
        "END:VEVENT\n" +
        "END:VCALENDAR";
  }

  private String getUrlEncodedString(String request) {
    return URLEncoder.encode(request, StandardCharsets.UTF_8);
  }

  private Map<String, String> getRequestForm(String scheduleIcalString) {
    Map<String, String> form = new HashMap<>();
    form.put("calendarId", "defaultCalendarId");
    form.put("scheduleIcalString", scheduleIcalString);
    return form;
  }


}
