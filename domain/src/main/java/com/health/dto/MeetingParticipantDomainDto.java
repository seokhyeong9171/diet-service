package com.health.dto;

import com.health.type.AdmissionStatus;
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
