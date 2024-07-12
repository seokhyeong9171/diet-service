package com.health.service.meetingservice.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.health.service.meetingservice.dto.CalenderDto;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CalenderComponent {

  private final ObjectMapper objectMapper;

  public CalenderDto.Response addCalender(String accessToken, CalenderDto.Request request) {

    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss");

    String header = "Bearer " + accessToken;

    try {
      String calSum =  URLEncoder.encode(request.getTitle(), StandardCharsets.UTF_8);
      String calDes =  URLEncoder.encode(request.getContent(), StandardCharsets.UTF_8);
      String calLoc =  URLEncoder.encode(request.getAddress(), StandardCharsets.UTF_8);
      String date = request.getMeetingDt().format(timeFormatter);
      String apiURL = "https://openapi.naver.com/calendar/createSchedule.json";

      URL url = new URL(apiURL);
      HttpURLConnection con = (HttpURLConnection)url.openConnection();
      con.setRequestMethod("POST");
      con.setRequestProperty("Authorization", header);
      String uid = UUID.randomUUID().toString(); // 일정 고유 아이디
      String scheduleIcalString = "BEGIN:VCALENDAR\n" +
          "VERSION:2.0\n" +
          "PRODID:Naver Calendar\n" +
          "CALSCALE:GREGORIAN\n" +
          "BEGIN:VTIMEZONE\n" +
          "TZID:Asia/Seoul\n" +
          "BEGIN:STANDARD\n" +
          "DTSTART:19700101T000000\n" +
          "TZNAME:GMT%2B09:00\n" +
          "TZOFFSETFROM:%2B0900\n" +
          "TZOFFSETTO:%2B0900\n" +
          "END:STANDARD\n" +
          "END:VTIMEZONE\n" +
          "BEGIN:VEVENT\n" +
          "SEQUENCE:0\n" +
          "CLASS:PUBLIC\n" +
          "TRANSP:OPAQUE\n" +
          "UID:" + uid + "\n" +
          "DTSTART;TZID=Asia/Seoul:" + date + "\n" +  // 시작 일시
          "DTEND;TZID=Asia/Seoul:" + date + "\n" +    // 종료 일시
          "SUMMARY:"+ calSum +" \n" +                    // 일정 제목
          "DESCRIPTION:"+ calDes +" \n" +                // 일정 상세 내용
          "LOCATION:"+ calLoc +" \n" +                   // 장소
          "CREATED:" + LocalDateTime.now().format(timeFormatter) + "Z\n" +         // 일정 생성시각
          "LAST-MODIFIED:" + LocalDateTime.now().format(timeFormatter) + "Z\n" +   // 일정 수정시각
          "DTSTAMP:" + LocalDateTime.now().format(timeFormatter) + "Z\n" +         // 일정 타임스탬프
          "END:VEVENT\n" +
          "END:VCALENDAR";


      String postParams = "calendarId=defaultCalendarId&scheduleIcalString=" + scheduleIcalString;
      con.setDoOutput(true);
      DataOutputStream wr = new DataOutputStream(con.getOutputStream());
      wr.writeBytes(postParams);
      wr.flush();
      wr.close();
      int responseCode = con.getResponseCode();
      BufferedReader br;
      if(responseCode==200) { // 정상 호출
        br = new BufferedReader(new InputStreamReader(con.getInputStream()));
      } else {  // 에러 발생
        br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
      }
      String inputLine;
      StringBuffer response = new StringBuffer();
      while ((inputLine = br.readLine()) != null) {
        response.append(inputLine);
      }
      br.close();
      return objectMapper.readValue(response.toString(), CalenderDto.Response.class);

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
