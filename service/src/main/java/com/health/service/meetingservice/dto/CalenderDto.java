package com.health.service.meetingservice.dto;

import com.health.domain.entity.MeetingEntity;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


public class CalenderDto {

  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  public static class Request {
    private String title;
    private String content;
    private String address;

    private LocalDateTime meetingDt;

    public static Request fromMeetingEntity(MeetingEntity meeting) {
      return Request.builder()
          .title(meeting.getMeetingName())
          .content(meeting.getMeetingDescription())
          .address(null)
          .build();
    }
  }

  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  @ToString
  public static class Response {

    private String result;
    private Integer code;
    private Value returnValue;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    private static class Value {
      private String calendarId;
      private String processType;
      private String icalUid;
    }

  }


}
