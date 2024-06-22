package com.health.userservice.service.impl;

import static com.health.common.exception.ErrorCode.EXERCISE_RECORD_NOT_FOUND;
import static com.health.common.exception.ErrorCode.USER_NOT_FOUND;

import com.health.common.exception.CustomException;
import com.health.domain.dto.ExerciseRecordDomainDto;
import com.health.domain.entity.ExerciseRecordEntity;
import com.health.domain.entity.UserEntity;
import com.health.domain.form.ExerciseRecordDomainForm;
import com.health.domain.repository.ExerciseRecordRepository;
import com.health.domain.repository.UserRepository;
import com.health.userservice.service.UserExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserExerciseServiceImpl implements UserExerciseService {

  private final UserRepository userRepository;
  private final ExerciseRecordRepository exerciseRecordRepository;


  @Override
  @Transactional(readOnly = true)
  public Page<ExerciseRecordDomainDto> getExerciseList(String authId, Pageable pageable) {

    // user 정보 존재하는지 확인
    validateExistUser(authId);

    // 페이징 처리해 반환
    return getUserExerciseRecordList(authId, pageable);
  }

  @Override
  public ExerciseRecordDomainDto createExerciseRecord(String authId, ExerciseRecordDomainForm form) {

    UserEntity findUser = findUserById(authId);

    // form으로 부터 record 생성
    ExerciseRecordEntity createdRecord = ExerciseRecordEntity.createRecord(findUser, form);
    // record 저장
    ExerciseRecordEntity savedRecord = exerciseRecordRepository.save(createdRecord);
    // user 엔티티의 record list에 저장
    findUser.getExerciseRecordEntityList().add(savedRecord);

    // dto로 반환
    return ExerciseRecordDomainDto.fromEntity(savedRecord);
  }

  @Override
  public ExerciseRecordDomainDto updateExerciseRecord(String authId, Long recordId,
      ExerciseRecordDomainForm form) {

    // user 정보 존재하는지 확인
    validateExistUser(authId);

    ExerciseRecordEntity findRecord = findExerciseRecordById(recordId);

    // record 업데이트
    findRecord.updateRecord(form);

    // dto로 반환
    return ExerciseRecordDomainDto.fromEntity(findRecord);
  }

  @Override
  public void deleteExerciseRecord(String authId, Long recordId) {

    UserEntity findUser = findUserById(authId);
    ExerciseRecordEntity findRecord = findExerciseRecordById(recordId);

    // user 엔티티의 record list에서 삭제
    findUser.getExerciseRecordEntityList().remove(findRecord);
    exerciseRecordRepository.delete(findRecord);
  }


  private void validateExistUser(String authId) {
    if (!userRepository.existsByAuthId(authId)) {
      throw new CustomException(USER_NOT_FOUND);
    }
  }

  private UserEntity findUserById(String authId) {
    return userRepository.findByAuthId(authId)
        .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
  }

  private Page<ExerciseRecordDomainDto> getUserExerciseRecordList(String authId, Pageable pageable) {
    return exerciseRecordRepository.findAllByUserAuthIdOrderById(authId, pageable)
        .map(ExerciseRecordDomainDto::fromEntity);
  }

  private ExerciseRecordEntity findExerciseRecordById(Long recordId) {
    return exerciseRecordRepository.findById(recordId)
        .orElseThrow(() -> new CustomException(EXERCISE_RECORD_NOT_FOUND));
  }
}
