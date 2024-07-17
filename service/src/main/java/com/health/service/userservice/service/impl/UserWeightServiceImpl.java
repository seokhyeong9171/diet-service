package com.health.service.userservice.service.impl;

import static com.health.domain.exception.ErrorCode.USER_NOT_FOUND;
import static com.health.domain.exception.ErrorCode.WEIGHT_RECORD_ALREADY_POSTED;
import static com.health.domain.exception.ErrorCode.WEIGHT_RECORD_NOT_FOUND;
import static com.health.domain.exception.ErrorCode.WEIGHT_RECORD_NOT_OWNED_USER;

import com.health.domain.entity.UserEntity;
import com.health.domain.entity.UserWeightEntity;
import com.health.domain.exception.CustomException;
import com.health.domain.repository.UserRepository;
import com.health.domain.repository.UserWeightRepository;
import com.health.domain.repository.query.UserWeightQueryRepository;
import com.health.service.userservice.dto.UserWeightServiceDto;
import com.health.service.userservice.form.UserWeightServiceForm;
import com.health.service.userservice.service.UserWeightService;
import com.health.service.userservice.type.Scope;
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
  public Page<UserWeightServiceDto> getUserWeightList
      (String authId, String scope, Pageable pageable) {

    UserEntity findUser = findUserByAuthId(authId);

    Page<UserWeightEntity> weightEntityList =
        switch (Scope.fromValue(scope)) {

          case DAY -> userWeightQueryRepository.findAllByUser(findUser, pageable);
          case WEEK -> userWeightQueryRepository.findWeeklyByUser(findUser, pageable);
          case MONTH -> userWeightQueryRepository.findMonthlyByUser(findUser, pageable);

        };

    return weightEntityList.map(UserWeightServiceDto::fromEntity);
  }

  @Override
  public UserWeightServiceDto createWeightRecord(String authId, UserWeightServiceForm serviceForm) {

    UserEntity findUser = findUserByAuthId(authId);

    // 오늘 이미 몸무게 정보 등록 했는지 확인
    validateAlreadyPostToday(findUser);

    UserWeightEntity createdWeight =
        UserWeightEntity.createByForm(findUser, serviceForm.toDomainForm());
    UserWeightEntity savedWeight = userWeightRepository.save(createdWeight);

    findUser.getUserWeightList().add(savedWeight);
    // 유저의 몸무게 정보 업데이트
    findUser.updateWeight(savedWeight);

    return UserWeightServiceDto.fromEntity(savedWeight);
  }

  @Override
  public UserWeightServiceDto updateWeightRecord
      (String authId, Long recordId, UserWeightServiceForm serviceForm) {

    UserWeightEntity findWeight = findWeightRecordById(recordId);

    // 해당 유저의 몸무게 정보인지 확인
    validateAccurateUser(authId, findWeight);

    findWeight.updateByForm(serviceForm.toDomainForm());

    return UserWeightServiceDto.fromEntity(findWeight);
  }

  @Override
  public Long deleteWeightRecord(String authId, Long recordId) {
    UserEntity findUser = findUserByAuthId(authId);
    UserWeightEntity findWeight = findWeightRecordById(recordId);

    // 해당 유저의 몸무게 정보인지 확인
    validateAccurateUser(authId, findWeight);

    findUser.getUserWeightList().remove(findWeight);
    userWeightRepository.delete(findWeight);

    return findWeight.getId();
  }

  private UserWeightEntity findWeightRecordById(Long recordId) {
    return userWeightRepository.findWithUserInfoById(recordId)
        .orElseThrow(() -> new CustomException(WEIGHT_RECORD_NOT_FOUND));
  }

  private UserEntity findUserByAuthId(String authId) {
    return userRepository.findByAuthId(authId)
        .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
  }

  private void validateAlreadyPostToday(UserEntity findUser) {
    if (userWeightRepository.existsByUserAndWeightRegDt(findUser, LocalDate.now())) {
      throw new CustomException(WEIGHT_RECORD_ALREADY_POSTED);
    }
  }

  private void validateAccurateUser(String authId, UserWeightEntity findWeight) {
    if (!findWeight.getUser().getAuthId().equals(authId)) {
      throw new CustomException(WEIGHT_RECORD_NOT_OWNED_USER);
    }
  }
}
