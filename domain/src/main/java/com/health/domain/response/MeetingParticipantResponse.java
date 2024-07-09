package com.health.domain.response;

import com.health.domain.dto.MeetingParticipantDomainDto;
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

    public static MeetingParticipantResponse fromDomainDto(MeetingParticipantDomainDto participant) {
        return MeetingParticipantResponse.builder()
            .id(participant.getId())
            .meetingId(participant.getMeetingId())
            .participantAuthId(participant.getParticipantAuthId())
            .admissionStatus(participant.getAdmissionStatus())
            .build();
    }

}
