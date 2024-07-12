package com.health.api.application;

import com.health.api.form.MeetingForm;
import com.health.service.meetingservice.dto.MeetingServiceDto;
import com.health.service.meetingservice.dto.MeetingParticipantServiceDto;
import com.health.domain.type.Region;
import com.health.service.meetingservice.service.MeetingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MeetingApplication {

  private final MeetingService meetingService;

  public Page<MeetingServiceDto> getMeetingList(Region region, Pageable pageable) {
    return meetingService.getMeetingList(region, pageable);
  }

  public MeetingServiceDto createMeeting(String authId, MeetingForm meetingForm) {
    return meetingService.createMeeting(authId, meetingForm.toServiceForm());
  }

  public MeetingServiceDto updateMeeting(String authId, Long meetingId, MeetingForm meetingForm) {
    return meetingService.updateMeeting(authId, meetingId, meetingForm.toServiceForm());
  }

  public Long deleteMeeting(String authId, Long meetingId) {
    return meetingService.deleteMeeting(authId, meetingId);
  }

  public Long enrollMeeting(String authId, Long meetingId) {
    return meetingService.enrollMeeting(authId, meetingId);
  }

  public MeetingParticipantServiceDto permitEnroll
      (String authId, Long meetingId, Long participantId) {
    return meetingService.permitEnroll(authId, meetingId, participantId);
  }

  public MeetingParticipantServiceDto declineEnroll
      (String authId, Long meetingId, Long participantId) {
    return meetingService.declineEnroll
        (authId, meetingId, participantId);
  }

  public Long setDemerit(String authId, Long meetingId, Long participantId) {
    return meetingService.setDemerit(authId, meetingId, participantId);
  }

  public Long cancelEnroll(String authId, Long meetingId, Long participantId) {
    return meetingService.cancelEnroll(authId, meetingId, participantId);
  }

  public void addCalender(String authId, Long meetingId, Long participantId) {
    meetingService.addCalender(authId, meetingId, participantId);
  }
}
