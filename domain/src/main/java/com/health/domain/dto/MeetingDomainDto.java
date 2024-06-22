package com.health.domain.dto;

import com.health.domain.type.MeetingStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MeetingDomainDto {

    private Long id;

    private String meetingName;

    private String meetingDescription;

    private Integer minParticipant;

    private Integer maxParticipant;

    private LocalDate meetingDeadLine;

    private GeoInformationDomainDto meetingArea;

    private LocalDateTime meetingDt;

    private MeetingStatus meetingStatus;

    private UserDomainDto establishedUser;

    private List<MeetingParticipantDomainDto> participantList;
}
