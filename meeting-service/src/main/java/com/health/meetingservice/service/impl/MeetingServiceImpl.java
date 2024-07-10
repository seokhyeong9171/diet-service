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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MeetingServiceImpl implements MeetingService {

  private final UserRepository userRepository;
  private final MeetingRepository meetingRepository;
  private final MeetingParticipantRepository meetingParticipantRepository;

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

    meetingRepository.delete(findMeeting);

    // 모임 상태 cancel로 변경, 참가자 상태 cancel로 변경
    findMeeting.cancel();
    findMeeting.getParticipantList().forEach(MeetingParticipantEntity::cancel);

    return findMeeting.getId();
  }

  @Override
  public Long enrollMeeting(String authId, Long meetingId) {

    UserEntity findUser = findUserByAuthId(authId);
    MeetingEntity findMeeting = findMeetingById(meetingId);

    validateBlacklist(findUser);

    RLock participateLock = redissonClient.getLock(meetingEnrollRock(meetingId));

    validateHaveRightToEnroll(findUser, findMeeting);

    try {

      // 선착순 참가 시 동시성 문제 해결 위해 lock 사용
      boolean isParticipantLock =
          participateLock.tryLock(10, 10, TimeUnit.SECONDS);

      if (!isParticipantLock) {
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
  public Long cancelEnroll(String authId, Long meetingId, Long participantId) {

    UserEntity findUser = findUserByAuthId(authId);
    MeetingEntity findMeeting = findMeetingById(meetingId);
    MeetingParticipantEntity findParticipant = findParticipantById(participantId);

    validateMeetingAndParticipant(findMeeting, findParticipant);
    validateParticipantUser(findParticipant, findUser);

    findParticipant.cancel();
    hashOperations.increment(meetingParticipantCount(), meetingId.toString(), -1);

    return findParticipant.getId();
  }


  @Override
  public MeetingParticipantDomainDto permitEnroll
      (String authId, Long meetingId, Long participantId) {

    UserEntity findUser = findUserByAuthId(authId);
    MeetingEntity findMeeting = findMeetingById(meetingId);
    MeetingParticipantEntity findParticipant = findParticipantById(participantId);

    validateBlacklist(findParticipant.getParticipant());
    validateParticipantStatus(findParticipant);
    validateMeetingCreator(findUser, findMeeting);
    validateMeetingAndParticipant(findMeeting, findParticipant);

    findParticipant.permit();

    return MeetingParticipantDomainDto.fromEntity(findParticipant);
  }

  @Override
  public MeetingParticipantDomainDto declineEnroll
      (String authId, Long meetingId, Long participantId) {

    UserEntity findUser = findUserByAuthId(authId);
    MeetingEntity findMeeting = findMeetingById(meetingId);
    MeetingParticipantEntity findParticipant = findParticipantById(participantId);

    validateParticipantStatus(findParticipant);
    validateMeetingCreator(findUser, findMeeting);
    validateMeetingAndParticipant(findMeeting, findParticipant);

    findParticipant.decline();

    hashOperations.increment(meetingParticipantCount(), meetingId.toString(), -1);

    return MeetingParticipantDomainDto.fromEntity(findParticipant);
  }

  @Override
  public Long setDemerit(String authId, Long meetingId, Long participantId) {

    UserEntity findUser = findUserByAuthId(authId);
    MeetingEntity findMeeting = findMeetingById(meetingId);
    MeetingParticipantEntity findParticipant = findParticipantById(participantId);

    validateMeetingCreator(findUser, findMeeting);
    validateMeetingAndParticipant(findMeeting, findParticipant);

    findParticipant.getParticipant().increaseDemerit();

    return findParticipant.getParticipant().getId();
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

  // 모임 참가자 수가 다 찼는지 확인
  private void validateParticipantCount(Long meetingId, MeetingEntity findMeeting) {
    if (findMeeting.getMaxParticipant() >= getParticipantCount(meetingId)) {
      throw new CustomException(MEETING_PARTICIPANT_FULL);
    }
  }

  // 참가 권한이 있는지 확인
  private void validateHaveRightToEnroll(UserEntity findUser, MeetingEntity findMeeting) {
    validateBlacklist(findUser);
    validateNotMeetingCreator(findUser, findMeeting);
  }

  // 해당 user가 blacklist인지 확인
  private void validateBlacklist(UserEntity findUser) {
    if (findUser.isBlacklist()) {
      throw new CustomException(USER_BLACKLIST);
    }
  }

  // 해당 user가 모임 개설자인지 확인
  private void validateMeetingCreator(UserEntity findUser, MeetingEntity findMeeting) {
    if (findUser != findMeeting.getEstablishedUser()) {
      throw new CustomException(MEETING_CREATOR_NOT_MATCH);
    }
  }

  // 해당 user가 모임 개설자가 아닌지 확인
  private void validateNotMeetingCreator(UserEntity findUser, MeetingEntity findMeeting) {
    if (findUser == findMeeting.getEstablishedUser()) {
      throw new CustomException(MEETING_CREATOR_CAN_NOT_ENROLL);
    }
  }

  // 해당 참가의 유저 정보 일치하는지 확인
  private void validateParticipantUser
      (MeetingParticipantEntity findParticipant, UserEntity findUser) {
    if (findUser != findParticipant.getParticipant()) {
      throw new CustomException(MEETING_PARTICIPANT_AND_USER_NOT_MATCH);
    }
  }

  // 현재 participant 상태가 pending인지 확인 (pending 상태에서만 승인, 거절 가능)
  private void validateParticipantStatus(MeetingParticipantEntity findParticipant) {
    if (findParticipant.getAdmissionStatus() != PENDING) {
      throw new CustomException(MEETING_PARTICIPANT_STATUS_NOT_PENDING);
    }
  }

  // 해당 meeting과 participant 정보가 일치하는지 확인
  private void validateMeetingAndParticipant(MeetingEntity findMeeting,
      MeetingParticipantEntity findParticipant) {
    if (findMeeting != findParticipant.getMeeting()) {
      throw new CustomException(MEETING_PARTICIPANT_AND_MEETING_NOT_MATCH);
    }
  }
}
