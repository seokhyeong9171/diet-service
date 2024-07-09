package com.health.api.controller;

import com.health.api.form.MeetingForm;
import com.health.api.service.MeetingApplication;
import com.health.common.model.SuccessResponse;
import com.health.domain.dto.MeetingDomainDto;
import com.health.domain.dto.MeetingParticipantDomainDto;
import com.health.domain.response.MeetingParticipantResponse;
import com.health.domain.response.MeetingResponse;
import com.health.domain.type.Region;
import com.health.security.authentication.AuthValidatorComponent;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/meeting")
@RequiredArgsConstructor
public class MeetingController {

  private final AuthValidatorComponent authValidatorComponent;
  private final MeetingApplication meetingApplication;

  @GetMapping
  public ResponseEntity<?> getMeetingList(@RequestParam Region region, Pageable pageable) {

    Page<MeetingDomainDto> meetingDomainDtoList =
        meetingApplication.getMeetingList(region, pageable);

    return ResponseEntity.ok(
        SuccessResponse.of(meetingDomainDtoList.stream().map(MeetingResponse::fromDomainDto))
    );
  }


  @PostMapping
  public ResponseEntity<?> createMeeting(
      @CookieValue("Authorization") String jwt,
      @RequestBody @Validated MeetingForm meetingForm
  ) {

    String authId = authValidatorComponent.validateAuthId(jwt);

    MeetingDomainDto meetingDomainDto = meetingApplication.createMeeting(authId, meetingForm);

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(SuccessResponse.of(MeetingResponse.fromDomainDto(meetingDomainDto)));
  }

  @PatchMapping("/{meetingId}")
  public ResponseEntity<?> updateMeeting(
      @CookieValue("Authorization") String jwt,
      @RequestBody @Validated MeetingForm meetingForm, @PathVariable Long meetingId
  ) {

    String authId = authValidatorComponent.validateAuthId(jwt);

    MeetingDomainDto meetingDomainDto =
        meetingApplication.updateMeeting(authId, meetingId, meetingForm);

    return ResponseEntity.ok(SuccessResponse.of(MeetingResponse.fromDomainDto(meetingDomainDto)));
  }

  @DeleteMapping("/{meetingId}")
  public ResponseEntity<?> deleteMeeting(
      @CookieValue("Authorization") String jwt, @PathVariable Long meetingId
  ) {

    String authId = authValidatorComponent.validateAuthId(jwt);

    Long deletedMeetingId = meetingApplication.deleteMeeting(authId, meetingId);

    return ResponseEntity.ok(SuccessResponse.of(deletedMeetingId));
  }

  @PostMapping("/{meetingId}")
  public ResponseEntity<?> enrollMeeting(
      @CookieValue("Authorization") String jwt, @PathVariable Long meetingId
  ) {

    String authId = authValidatorComponent.validateAuthId(jwt);

    Long enrolledMeetingId = meetingApplication.enrollMeeting(authId, meetingId);

    return ResponseEntity.ok(SuccessResponse.of(enrolledMeetingId));
  }

  @PostMapping("/{meetingId}/participant/{participantId}/enroll")
  public ResponseEntity<?> permitEnroll(
      @CookieValue("Authorization") String jwt, @PathVariable Long meetingId,
      @PathVariable Long participantId
  ) {

    String authId = authValidatorComponent.validateAuthId(jwt);

    MeetingParticipantDomainDto meetingParticipantDomainDto =
        meetingApplication.permitEnroll(authId, meetingId, participantId);

    return ResponseEntity.ok(
        SuccessResponse.of(MeetingParticipantResponse.fromDomainDto(meetingParticipantDomainDto))
    );
  }

  @PostMapping("/{meetingId}/participant/{participantId}/decline")
  public ResponseEntity<?> delcineEnroll(
      @CookieValue("Authorization") String jwt, @PathVariable Long meetingId,
      @PathVariable Long participantId
  ) {

    String authId = authValidatorComponent.validateAuthId(jwt);

    MeetingParticipantDomainDto meetingParticipantDomainDto =
        meetingApplication.declineEnroll(authId, meetingId, participantId);

    return ResponseEntity.ok(
        SuccessResponse.of(MeetingParticipantResponse.fromDomainDto(meetingParticipantDomainDto))
    );
  }

  @PostMapping("/{meetingId}/participant/{participantId}/demerit")
  public ResponseEntity<?> setDemerit(
      @CookieValue("Authorization") String jwt, @PathVariable Long meetingId,
      @PathVariable Long participantId
  ) {

    String authId = authValidatorComponent.validateAuthId(jwt);

    Long demeritedUserAuthId = meetingApplication.setDemerit(authId, meetingId, participantId);

    return ResponseEntity.ok(SuccessResponse.of(demeritedUserAuthId));
  }

}
