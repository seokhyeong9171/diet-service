package com.health.domain.form;

import com.health.domain.type.Region;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MeetingDomainForm {

    private String meetingName;

    private String meetingDescription;

    private Integer minParticipant;

    private Integer maxParticipant;

    private LocalDate meetingDeadLine;

    private MeetingArea meetingArea;

    private LocalDateTime meetingDt;


    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class MeetingArea {
        private Region region;
        private Double latitude;
        private Double longitude;
    }

}
