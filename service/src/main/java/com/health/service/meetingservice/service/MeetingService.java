package com.health.service.meetingservice.service;

import com.health.domain.type.Region;
import com.health.service.meetingservice.dto.MeetingParticipantServiceDto;
import com.health.service.meetingservice.dto.MeetingServiceDto;
import com.health.service.meetingservice.form.MeetingServiceForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MeetingService {

  Page<MeetingServiceDto> getMeetingList(Region region, Pageable pageable);

  MeetingServiceDto createMeeting(String authId, MeetingServiceForm serviceForm);

  MeetingServiceDto updateMeeting(String authId, Long meetingId, MeetingServiceForm serviceForm);

  Long deleteMeeting(String authId, Long meetingId);

  Long enrollMeeting(String authId, Long meetingId);

  MeetingParticipantServiceDto permitEnroll(String authId, Long meetingId, Long participantId);

  MeetingParticipantServiceDto declineEnroll(String authId, Long meetingId, Long participantId);

  Long setDemerit(String authId, Long meetingId, Long participantId);

  Long cancelEnroll(String authId, Long meetingId, Long participantId);

  void addCalender(String authId, Long meetingId, Long participantId);

}
