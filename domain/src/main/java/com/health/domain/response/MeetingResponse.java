package com.health.domain.response;

import com.health.domain.dto.MeetingDomainDto;
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

    public static MeetingResponse fromDomainDto(MeetingDomainDto meetingDomainDto) {
        return MeetingResponse.builder()
            .id(meetingDomainDto.getId())
            .meetingName(meetingDomainDto.getMeetingName())
            .meetingDescription(meetingDomainDto.getMeetingDescription())
            .minParticipant(meetingDomainDto.getMinParticipant())
            .maxParticipant(meetingDomainDto.getMaxParticipant())
            .meetingDeadLine(meetingDomainDto.getMeetingDeadLine())
            .meetingArea(meetingDomainDto.getMeetingArea())
            .meetingDt(meetingDomainDto.getMeetingDt())
            .meetingStatus(meetingDomainDto.getMeetingStatus())
            .establishedUserAuthId(meetingDomainDto.getEstablishedUserAuthId())
            .establishedUserNickName(meetingDomainDto.getEstablishedUserAuthId())
            .build();
    }

}
