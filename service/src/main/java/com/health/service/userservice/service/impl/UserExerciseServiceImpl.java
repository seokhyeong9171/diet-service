package com.health.service.userservice.service.impl;

import static com.health.domain.exception.ErrorCode.EXERCISE_RECORD_ALREADY_POSTED;
import static com.health.domain.exception.ErrorCode.EXERCISE_RECORD_EXCEED_DELETE_DATE;
import static com.health.domain.exception.ErrorCode.EXERCISE_RECORD_NOT_FOUND;
import static com.health.domain.exception.ErrorCode.EXERCISE_RECORD_NOT_OWNED_USER;
import static com.health.domain.exception.ErrorCode.USER_NOT_FOUND;

import com.health.domain.entity.ExerciseRecordEntity;
import com.health.domain.entity.UserEntity;
import com.health.domain.exception.CustomException;
import com.health.domain.repository.ExerciseRecordRepository;
import com.health.domain.repository.UserRepository;
import com.health.service.userservice.dto.ExerciseRecordServiceDto;
import com.health.service.userservice.form.ExerciseRecordServiceForm;
import com.health.service.userservice.service.UserExerciseService;
import java.time.LocalDate;
import java.util.Objects;
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
  public Page<ExerciseRecordServiceDto> getExerciseList(String authId, Pageable pageable) {

    // user 정보 존재하는지 확인
    validateExistUser(authId);

    // 페이징 처리해 반환
    return getUserExerciseRecordList(authId, pageable);
  }

  @Override
  public ExerciseRecordServiceDto createExerciseRecord(String authId, ExerciseRecordServiceForm serviceForm) {

    UserEntity findUser = findUserById(authId);

    // 오늘 이미 등록한 상태인지 확인
    validatePostRecordToday(findUser);

    // form으로 부터 record 생성
    ExerciseRecordEntity createdRecord =
        ExerciseRecordEntity.createRecord(findUser, serviceForm.toDomainForm());
    // record 저장
    ExerciseRecordEntity savedRecord = exerciseRecordRepository.save(createdRecord);
    // user 엔티티의 record list에 저장
    findUser.getExerciseRecordEntityList().add(savedRecord);
    // user의 exercise duration 증가
    findUser.increaseExerciseDuration();

    // dto로 반환
    return ExerciseRecordServiceDto.fromEntity(savedRecord);
  }

  @Override
  public ExerciseRecordServiceDto updateExerciseRecord(String authId, Long recordId,
      ExerciseRecordServiceForm serviceForm) {

    // user 정보 존재하는지 확인
    validateExistUser(authId);

    ExerciseRecordEntity findRecord = findExerciseRecordById(recordId);

    // 올바른 소유자의 기록인지 확인
    validateAccurateUser(authId, findRecord);

    // record 업데이트
    findRecord.updateRecord(serviceForm.toDomainForm());

    // dto로 반환
    return ExerciseRecordServiceDto.fromEntity(findRecord);
  }

  @Override
  public void deleteExerciseRecord(String authId, Long recordId) {

    UserEntity findUser = findUserById(authId);
    ExerciseRecordEntity findRecord = findExerciseRecordById(recordId);

    // exercise record는 당일에만 삭제 가능
    validateIsToday(findRecord);

    // 올바른 소유자의 기록인지 확인
    validateAccurateUser(authId, findRecord);

    // user 엔티티의 record list에서 삭제
    findUser.getExerciseRecordEntityList().remove(findRecord);

    // user의 exercise duration 감소
    findUser.decreaseExerciseDuration();
    exerciseRecordRepository.delete(findRecord);

  }


  private void validateExistUser(String authId) {
    if (!userRepository.existsByAuthId(authId)) {
      throw new CustomException(USER_NOT_FOUND);
    }
  }

  private void validateAccurateUser(String authId, ExerciseRecordEntity findRecord) {
    if (!findRecord.getUser().getAuthId().equals(authId)) {
      throw new CustomException(EXERCISE_RECORD_NOT_OWNED_USER);
    }
  }

  private void validatePostRecordToday(UserEntity findUser) {

    if (exerciseRecordRepository.existsByUserAndExerciseRecDt(findUser, LocalDate.now())) {
      throw new CustomException(EXERCISE_RECORD_ALREADY_POSTED);
    }
  }

  private void validateIsToday(ExerciseRecordEntity findRecord) {
    if (!Objects.equals(findRecord.getExerciseRecDt(), LocalDate.now())) {
      throw new CustomException(EXERCISE_RECORD_EXCEED_DELETE_DATE);
    }
  }

  private UserEntity findUserById(String authId) {
    return userRepository.findByAuthId(authId)
        .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
  }

  private Page<ExerciseRecordServiceDto> getUserExerciseRecordList(String authId, Pageable pageable) {
    return exerciseRecordRepository.findAllByUserAuthIdOrderById(authId, pageable)
        .map(ExerciseRecordServiceDto::fromEntity);
  }

  private ExerciseRecordEntity findExerciseRecordById(Long recordId) {
    return exerciseRecordRepository.findById(recordId)
        .orElseThrow(() -> new CustomException(EXERCISE_RECORD_NOT_FOUND));
  }
}
