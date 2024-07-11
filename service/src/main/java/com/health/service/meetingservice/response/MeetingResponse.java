package com.health.service.meetingservice.response;

import com.health.service.meetingservice.dto.MeetingServiceDto;
import com.health.domain.entity.GeoInformation;
import com.health.domain.type.MeetingStatus;
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
public class MeetingResponse {

    private Long id;

    private String meetingName;

    private String meetingDescription;

    private Integer minParticipant;

    private Integer maxParticipant;

    private LocalDate meetingDeadLine;

    private GeoInformation meetingArea;

    private LocalDateTime meetingDt;

    private MeetingStatus meetingStatus;

    private String establishedUserAuthId;
    private String establishedUserNickName;

    public static MeetingResponse fromDomainDto(MeetingServiceDto meetingServiceDto) {
        return MeetingResponse.builder()
            .id(meetingServiceDto.getId())
            .meetingName(meetingServiceDto.getMeetingName())
            .meetingDescription(meetingServiceDto.getMeetingDescription())
            .minParticipant(meetingServiceDto.getMinParticipant())
            .maxParticipant(meetingServiceDto.getMaxParticipant())
            .meetingDeadLine(meetingServiceDto.getMeetingDeadLine())
            .meetingArea(meetingServiceDto.getMeetingArea())
            .meetingDt(meetingServiceDto.getMeetingDt())
            .meetingStatus(meetingServiceDto.getMeetingStatus())
            .establishedUserAuthId(meetingServiceDto.getEstablishedUserAuthId())
            .establishedUserNickName(meetingServiceDto.getEstablishedUserAuthId())
            .build();
    }

}
