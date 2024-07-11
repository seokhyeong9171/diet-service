package com.health.meetingservice.form;

import com.health.domain.form.MeetingDomainForm;
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
public class MeetingServiceForm {

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
        private String address;
    }

    public MeetingDomainForm toDomainForm() {
        return MeetingDomainForm.builder()
            .meetingName(meetingName)
            .meetingDescription(meetingDescription)
            .minParticipant(minParticipant)
            .maxParticipant(maxParticipant)
            .meetingDeadLine(meetingDeadLine)
            .meetingArea(
                MeetingDomainForm.MeetingArea.builder()
                    .region(meetingArea.getRegion())
                    .address(meetingArea.getAddress())
                    .build()
            )
            .meetingDt(meetingDt)
            .build();
    }

}
