package com.health.meetingservice.service;

import com.health.domain.dto.MeetingDomainDto;
import com.health.domain.dto.MeetingParticipantDomainDto;
import com.health.domain.form.MeetingDomainForm;
import com.health.domain.type.Region;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MeetingService {

  Page<MeetingDomainDto> getMeetingList(Region region, Pageable pageable);

  MeetingDomainDto createMeeting(String authId, MeetingDomainForm domainForm);

  MeetingDomainDto updateMeeting(String authId, Long meetingId, MeetingDomainForm domainForm);

  Long deleteMeeting(String authId, Long meetingId);

  Long enrollMeeting(String authId, Long meetingId);

  MeetingParticipantDomainDto permitEnroll(String authId, Long meetingId, Long participantId);

  MeetingParticipantDomainDto declineEnroll(String authId, Long meetingId, Long participantId);

  Long setDemerit(String authId, Long meetingId, Long participantId);
}