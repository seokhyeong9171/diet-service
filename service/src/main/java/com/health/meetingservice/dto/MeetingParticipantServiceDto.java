package com.health.meetingservice.dto;

import com.health.domain.entity.MeetingParticipantEntity;
import com.health.domain.type.AdmissionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MeetingParticipantServiceDto {

    private Long id;

    private Long meetingId;

    private String participantAuthId;

    private AdmissionStatus admissionStatus;

    public static MeetingParticipantServiceDto fromEntity(MeetingParticipantEntity participant) {
        return MeetingParticipantServiceDto.builder()
            .id(participant.getId())
            .meetingId(participant.getMeeting().getId())
            .participantAuthId(participant.getParticipant().getAuthId())
            .admissionStatus(participant.getAdmissionStatus())
            .build();
    }
}
