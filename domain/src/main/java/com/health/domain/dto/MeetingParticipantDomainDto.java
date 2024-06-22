package com.health.domain.dto;

import com.health.domain.type.AdmissionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MeetingParticipantDomainDto {

    private Long id;

    private MeetingDomainDto meeting;

    private UserDomainDto participant;

    private AdmissionStatus admissionStatus;
}
