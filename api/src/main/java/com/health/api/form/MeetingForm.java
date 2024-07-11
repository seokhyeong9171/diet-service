package com.health.api.form;

import com.health.meetingservice.form.MeetingServiceForm;
import com.health.domain.type.Region;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class MeetingForm {

    @NotBlank
    @Size(min = 1, max = 10)
    private String meetingName;

    @NotBlank
    @Size(min = 1, max = 200)
    private String meetingDescription;

    @NotBlank
    private Integer minParticipant;

    @NotBlank
    private Integer maxParticipant;

    @NotBlank
    private LocalDate meetingDeadLine;

    @NotNull
    private MeetingArea meetingArea;

    @NotNull
    private LocalDateTime meetingDt;


    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class MeetingArea {
        @NotNull
        private Region region;
        @NotNull
        private Double latitude;
        @NotNull
        private Double longitude;
    }


    public MeetingServiceForm toDomainForm() {
        return MeetingServiceForm.builder()
            .meetingName(meetingName)
            .meetingDescription(meetingDescription)
            .minParticipant(minParticipant)
            .maxParticipant(maxParticipant)
            .meetingDeadLine(meetingDeadLine)
            .meetingArea(
                MeetingServiceForm.MeetingArea.builder()
                    .region(meetingArea.getRegion())
                    .latitude(meetingArea.getLatitude())
                    .longitude(meetingArea.getLongitude())
                    .build()
            )
            .meetingDt(meetingDt)
            .build();
    }

}
