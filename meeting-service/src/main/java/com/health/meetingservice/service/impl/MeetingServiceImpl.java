package com.health.meetingservice.service.impl;

import static com.health.common.exception.ErrorCode.*;
import static com.health.common.redis.RedisKeyComponent.*;
import static com.health.domain.type.AdmissionStatus.*;

import com.health.common.exception.CustomException;
import com.health.domain.dto.MeetingDomainDto;
import com.health.domain.dto.MeetingParticipantDomainDto;
import com.health.domain.entity.MeetingEntity;
import com.health.domain.entity.MeetingParticipantEntity;
import com.health.domain.entity.UserEntity;
import com.health.domain.form.MeetingDomainForm;
import com.health.domain.repository.MeetingParticipantRepository;
import com.health.domain.repository.MeetingRepository;
import com.health.domain.repository.UserRepository;
import com.health.domain.type.Region;
import com.health.meetingservice.service.MeetingService;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MeetingServiceImpl implements MeetingService {

  private final UserRepository userRepository;
  private final MeetingRepository meetingRepository;
  private final MeetingParticipantRepository meetingParticipantRepository;

  private final RedisTemplate<String, String> redisTemplate;
  private final RedissonClient redissonClient;
  private final HashOperations<String, String, Integer> hashOperations;

  @Override
  @Transactional(readOnly = true)
  public Page<MeetingDomainDto> getMeetingList(Region region, Pageable pageable) {

    Page<MeetingEntity> meetingEntityList = meetingRepository.findAllByRegion(region, pageable);

    return meetingEntityList.map(MeetingDomainDto::fromEntity);
  }

  @Override
  public MeetingDomainDto createMeeting(String authId, MeetingDomainForm domainForm) {

    UserEntity findUser = findUserByAuthId(authId);

    MeetingEntity createdMeeting = MeetingEntity.createFromForm(findUser, domainForm);
    MeetingEntity savedMeeting = meetingRepository.save(createdMeeting);

    return MeetingDomainDto.fromEntity(savedMeeting);
  }

  @Override
  public MeetingDomainDto updateMeeting
      (String authId, Long meetingId, MeetingDomainForm domainForm) {

    UserEntity findUser = findUserByAuthId(authId);
    MeetingEntity findMeeting = findMeetingById(meetingId);

    validateMeetingCreator(findUser, findMeeting);

    findMeeting.updateMeeting(domainForm);

    return MeetingDomainDto.fromEntity(findMeeting);
  }

  @Override
  public Long deleteMeeting(String authId, Long meetingId) {

    UserEntity findUser = findUserByAuthId(authId);
    MeetingEntity findMeeting = findMeetingById(meetingId);

    validateMeetingCreator(findUser, findMeeting);

    // TODO
    //  meeting participant가 있을 경우 status만 cancel로 변경
    //  meeting participant 없을 경우 삭제

    meetingRepository.delete(findMeeting);

    if (findMeeting.getParticipantList().isEmpty()) {
      meetingRepository.delete(findMeeting);
    } else {
      findMeeting.cancel();
    }

    return findMeeting.getId();
  }

  @Override
  public Long enrollMeeting(String authId, Long meetingId) {

    UserEntity findUser = findUserByAuthId(authId);
    MeetingEntity findMeeting = findMeetingById(meetingId);

    RLock participateLock = redissonClient.getLock(meetingEnrollRock(meetingId));
    boolean isLock = false;


    validateHaveRightToEnroll(findUser, findMeeting);

    try {

      isLock = participateLock.tryLock(10, 10, TimeUnit.SECONDS);

      if (!isLock) {
        throw new CustomException(REDIS_LOCK_TIMEOUT);
      }
      validateParticipantCount(meetingId, findMeeting);

      hashOperations.increment(meetingParticipantCount(), meetingId.toString(), 1);

      MeetingParticipantEntity createdParticipant =
          MeetingParticipantEntity.enroll(findUser, findMeeting);

      MeetingParticipantEntity savedParticipant =
          meetingParticipantRepository.save(createdParticipant);

      findMeeting.getParticipantList().add(savedParticipant);
      return findMeeting.getId();

    } catch (RuntimeException | InterruptedException e) {
      throw new RuntimeException(e);

    } finally {
      participateLock.unlock();
    }

  }

  @Override
  public MeetingParticipantDomainDto permitEnroll
      (String authId, Long meetingId, Long participantId) {

    UserEntity findUser = findUserByAuthId(authId);
    MeetingEntity findMeeting = findMeetingById(meetingId);
    MeetingParticipantEntity findParticipant = findParticipantById(participantId);

    validateBlackList(findParticipant.getParticipant());
    validateParticipantStatus(findParticipant);
    validateMeetingCreator(findUser, findMeeting);
    validateMeetingAndParticipant(findMeeting, findParticipant);

    findParticipant.permit();

    return MeetingParticipantDomainDto.fromEntity(findParticipant);
  }

  private Integer getParticipantCount(Long meetingId) {
    Integer count = hashOperations.get(meetingParticipantCount(), meetingId.toString());
    return count == null ? 0 : count;
  }

  private UserEntity findUserByAuthId(String authId) {
    return userRepository.findByAuthId(authId)
        .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
  }

  private MeetingEntity findMeetingById(Long meetingId) {
    return meetingRepository.findById(meetingId)
        .orElseThrow(() -> new CustomException(MEETING_NOT_FOUND));
  }

  private MeetingParticipantEntity findParticipantById(Long participantId) {
    return meetingParticipantRepository.findById(participantId)
        .orElseThrow(() -> new CustomException(MEETING_PARTICIPANT_NOT_FOUND));
  }

  private void validateParticipantCount(Long meetingId, MeetingEntity findMeeting) {
    if (findMeeting.getMaxParticipant() >= getParticipantCount(meetingId)) {
      throw new CustomException(MEETING_PARTICIPANT_FULL);
    }
  }

  private void validateHaveRightToEnroll(UserEntity findUser, MeetingEntity findMeeting) {
    validateBlackList(findUser);
    validateNotMeetingCreator(findUser, findMeeting);
  }

  private void validateBlackList(UserEntity findUser) {
    if (findUser.getDemerit() >= 3) {
      throw new CustomException(USER_BLACKLIST);
    }
  }

  private void validateMeetingCreator(UserEntity findUser, MeetingEntity findMeeting) {
    if (findUser != findMeeting.getEstablishedUser()) {
      throw new CustomException(MEETING_CREATOR_NOT_MATCH);
    }
  }

  private void validateNotMeetingCreator(UserEntity findUser, MeetingEntity findMeeting) {
    if (findUser == findMeeting.getEstablishedUser()) {
      throw new CustomException(MEETING_CREATOR_CAN_NOT_ENROLL);
    }
  }

  private void validateParticipantStatus(MeetingParticipantEntity findParticipant) {
    if (findParticipant.getAdmissionStatus() != PENDING) {
      throw new CustomException(MEETING_PARTICIPANT_STATUS_INVALID);
    }
  }

  private void validateMeetingAndParticipant(MeetingEntity findMeeting, MeetingParticipantEntity findParticipant) {
    if (findMeeting != findParticipant.getMeeting()) {
      throw new CustomException(MEETING_PARTICIPANT_AND_MEETING_NOT_MATCH);
    }
  }
}
