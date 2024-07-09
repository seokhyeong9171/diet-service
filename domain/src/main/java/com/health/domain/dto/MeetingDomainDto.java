package com.health.domain.dto;

import com.health.domain.entity.GeoInformation;
import com.health.domain.entity.MeetingEntity;
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
public class MeetingDomainDto {

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

    public static MeetingDomainDto fromEntity(MeetingEntity meetingEntity) {
        return MeetingDomainDto.builder()
            .id(meetingEntity.getId())
            .meetingName(meetingEntity.getMeetingName())
            .meetingDescription(meetingEntity.getMeetingDescription())
            .minParticipant(meetingEntity.getMinParticipant())
            .maxParticipant(meetingEntity.getMaxParticipant())
            .meetingDeadLine(meetingEntity.getMeetingDeadLine())
            .meetingArea(meetingEntity.getMeetingArea())
            .meetingDt(meetingEntity.getMeetingDt())
            .meetingStatus(meetingEntity.getMeetingStatus())
            .establishedUserAuthId(meetingEntity.getEstablishedUser().getAuthId())
            .establishedUserNickName(meetingEntity.getEstablishedUser().getNickname())
            .build();
    }

}
