package com.health.userservice.service.impl;

import static com.health.common.exception.ErrorCode.PARAMETER_INVALID;
import static com.health.common.exception.ErrorCode.USER_NOT_FOUND;
import static com.health.common.exception.ErrorCode.WEIGHT_RECORD_ALREADY_POSTED;
import static com.health.common.exception.ErrorCode.WEIGHT_RECORD_NOT_FOUND;

import com.health.common.exception.CustomException;
import com.health.domain.dto.UserWeightDomainDto;
import com.health.domain.entity.UserEntity;
import com.health.domain.entity.UserWeightEntity;
import com.health.domain.form.UserWeightDomainForm;
import com.health.domain.repository.UserRepository;
import com.health.domain.repository.UserWeightRepository;
import com.health.domain.repository.query.UserWeightQueryRepository;
import com.health.userservice.service.UserWeightService;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserWeightServiceImpl implements UserWeightService {

  private final UserRepository userRepository;
  private final UserWeightRepository userWeightRepository;
  private final UserWeightQueryRepository userWeightQueryRepository;


  @Override
  @Transactional(readOnly = true)
  public Page<UserWeightDomainDto> getUserWeightList
      (String authId, String scope, Pageable pageable) {

    UserEntity findUser = findUserById(authId);

    Page<UserWeightEntity> weightEntityList =
        switch (scope) {

          case "day" -> userWeightRepository.findAllByUser(findUser, pageable);
          case "week" -> userWeightQueryRepository.findWeeklyByUser(findUser, pageable);
          case "month" -> userWeightQueryRepository.findMonthlyByUser(findUser, pageable);

          default -> throw new CustomException(PARAMETER_INVALID);
        };

    return weightEntityList.map(UserWeightDomainDto::fromEntity);
  }

  @Override
  public UserWeightDomainDto createWeightRecord(String authId, UserWeightDomainForm form) {

    UserEntity findUser = findUserById(authId);

    if (userWeightRepository.existsByUserAndWeightRegDt(findUser, LocalDate.now())) {
      throw new CustomException(WEIGHT_RECORD_ALREADY_POSTED);
    }

    UserWeightEntity createdWeight = UserWeightEntity.createByForm(findUser, form);
    UserWeightEntity savedWeight = userWeightRepository.save(createdWeight);
    findUser.getUserWeightList().add(savedWeight);

    return UserWeightDomainDto.fromEntity(savedWeight);
  }

  @Override
  public UserWeightDomainDto updateWeightRecord
      (String authId, Long recordId, UserWeightDomainForm form) {

    UserWeightEntity findWeight = findWeightRecordById(recordId);
    findWeight.updateByForm(form);

    return UserWeightDomainDto.fromEntity(findWeight);
  }

  @Override
  public Long deleteWeightRecord(String authId, Long recordId) {
    UserEntity findUser = findUserById(authId);
    UserWeightEntity findWeightRecord = findWeightRecordById(recordId);

    findUser.getUserWeightList().remove(findWeightRecord);
    userWeightRepository.delete(findWeightRecord);

    return findWeightRecord.getId();
  }

  private UserWeightEntity findWeightRecordById(Long recordId) {
    return userWeightRepository.findWithUserInfoById(recordId)
        .orElseThrow(() -> new CustomException(WEIGHT_RECORD_NOT_FOUND));
  }


  private UserEntity findUserById(String authId) {
    return userRepository.findByAuthId(authId)
        .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
  }
}
