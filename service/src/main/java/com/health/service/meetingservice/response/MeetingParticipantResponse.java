package com.health.service.meetingservice.response;

import com.health.service.meetingservice.dto.MeetingParticipantServiceDto;
import com.health.domain.type.AdmissionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MeetingParticipantResponse {

    private Long id;

    private Long meetingId;

    private String participantAuthId;

    private AdmissionStatus admissionStatus;

    public static MeetingParticipantResponse fromDomainDto(MeetingParticipantServiceDto participant) {
        return MeetingParticipantResponse.builder()
            .id(participant.getId())
            .meetingId(participant.getMeetingId())
            .participantAuthId(participant.getParticipantAuthId())
            .admissionStatus(participant.getAdmissionStatus())
            .build();
    }

}
