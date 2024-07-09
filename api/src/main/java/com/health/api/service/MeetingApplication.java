package com.health.api.service;

import com.health.api.form.MeetingForm;
import com.health.domain.dto.MeetingDomainDto;
import com.health.domain.dto.MeetingParticipantDomainDto;
import com.health.domain.type.Region;
import com.health.meetingservice.service.MeetingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MeetingApplication {

  private final MeetingService meetingService;

  public Page<MeetingDomainDto> getMeetingList(Region region, Pageable pageable) {
    return meetingService.getMeetingList(region, pageable);
  }

  public MeetingDomainDto createMeeting(String authId, MeetingForm meetingForm) {
    return meetingService.createMeeting(authId, meetingForm.toDomainForm());
  }

  public MeetingDomainDto updateMeeting(String authId, Long meetingId, MeetingForm meetingForm) {
    return meetingService.updateMeeting(authId, meetingId, meetingForm.toDomainForm());
  }

  public Long deleteMeeting(String authId, Long meetingId) {
    return meetingService.deleteMeeting(authId, meetingId);
  }

  public Long enrollMeeting(String authId, Long meetingId) {
    return meetingService.enrollMeeting(authId, meetingId);
  }

  public MeetingParticipantDomainDto permitEnroll
      (String authId, Long meetingId, Long participantId) {
    return meetingService.permitEnroll(authId, meetingId, participantId);
  }
}
