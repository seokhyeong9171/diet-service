package com.health.api.service;

import static com.health.common.exception.ErrorCode.EXERCISE_RECORD_ALREADY_POSTED;
import static com.health.common.exception.ErrorCode.EXERCISE_RECORD_EXCEED_DELETE_DATE;
import static com.health.common.exception.ErrorCode.EXERCISE_RECORD_NOT_OWNED_USER;
import static com.health.common.exception.ErrorCode.USER_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.health.api.form.ExerciseRecordForm;
import com.health.common.exception.CustomException;
import com.health.domain.dto.ExerciseRecordDomainDto;
import com.health.domain.entity.ExerciseRecordEntity;
import com.health.domain.entity.UserEntity;
import com.health.domain.repository.ExerciseRecordRepository;
import com.health.domain.repository.UserRepository;
import com.health.domain.type.Gender;
import com.health.domain.type.RoleType;
import com.health.domain.util.NicknameUtil;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ComponentScan("com.health")
@Transactional
@EntityScan(basePackages = "com.health.domain")
class ApiUserExerciseServiceTest {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private ExerciseRecordRepository exerciseRecordRepository;
  @Autowired
  private ApiUserExerciseService apiUserExerciseService;

  @Test
  @DisplayName("운동 정보 조회 - 성공")
  void getExerciseList() throws Exception {
    // given
    UserEntity userEntity1 = getUserEntity(1);
    UserEntity userEntity2 = getUserEntity(2);
    UserEntity savedUser1 = userRepository.save(userEntity1);
    UserEntity savedUser2 = userRepository.save(userEntity2);

    ExerciseRecordEntity exerciseRecord1 = getExerciseRecord(userEntity1, LocalDate.now());
    ExerciseRecordEntity exerciseRecord2 = getExerciseRecord(userEntity2, LocalDate.now());
    ExerciseRecordEntity savedExercise1 = exerciseRecordRepository.save(exerciseRecord1);
    ExerciseRecordEntity savedExercise2 = exerciseRecordRepository.save(exerciseRecord2);

    Pageable pageable = PageRequest.of(0, 10);

    // when
    Page<ExerciseRecordDomainDto> exerciseList =
        apiUserExerciseService.getExerciseList(savedUser1.getAuthId(), pageable);

    // then
    assertThat(exerciseList.getSize()).isEqualTo(10);
    assertThat(exerciseList.getTotalElements()).isEqualTo(1);
    assertThat(exerciseList.getContent().getFirst().getDescription())
        .isEqualTo(savedExercise1.getDescription());
  }

  @Test
  @DisplayName("운동 정보 생성 - 성공")
  void createExerciseRecordTest() throws Exception {
    // given
    UserEntity userEntity1 = getUserEntity(1);
    UserEntity userEntity2 = getUserEntity(2);
    UserEntity savedUser1 = userRepository.save(userEntity1);
    UserEntity savedUser2 = userRepository.save(userEntity2);

    ExerciseRecordForm exerciseRecordForm =
        ExerciseRecordForm.builder().description("description").build();

    // when
    ExerciseRecordDomainDto recordDomainDto =
        apiUserExerciseService.createExerciseRecord(savedUser1.getAuthId(), exerciseRecordForm);

    // then
    assertThat(recordDomainDto.getDescription()).isEqualTo(exerciseRecordForm.getDescription());
    assertThat(recordDomainDto.getUser()).isEqualTo(savedUser1);
    assertThat(recordDomainDto.getExerciseRecDt()).isEqualTo(LocalDate.now());
    assertThat(savedUser1.getExerciseRecordEntityList().size()).isEqualTo(1);
  }

  @Test
  @DisplayName("운동 정보 생성 - 실패 - 오늘 이미 생성")
  void createExerciseRecordTest_fail_already_posted_today() throws Exception {
    // given
    UserEntity userEntity1 = getUserEntity(1);
    UserEntity savedUser1 = userRepository.save(userEntity1);

    ExerciseRecordForm exerciseRecordForm =
        ExerciseRecordForm.builder().description("description").build();

    ExerciseRecordEntity exerciseRecord = getExerciseRecord(userEntity1, LocalDate.now());
    exerciseRecordRepository.save(exerciseRecord);

    // when
    // then
    assertThatThrownBy(() -> apiUserExerciseService
        .createExerciseRecord(savedUser1.getAuthId(), exerciseRecordForm)
    ).isInstanceOf(CustomException.class)
        .hasMessage(EXERCISE_RECORD_ALREADY_POSTED.getMessage());

  }

  @Test
  @DisplayName("운동 정보 수정 - 성공")
  void updateExerciseRecordTest() throws Exception {
    // given
    UserEntity userEntity1 = getUserEntity(1);
    UserEntity userEntity2 = getUserEntity(2);
    UserEntity savedUser1 = userRepository.save(userEntity1);
    UserEntity savedUser2 = userRepository.save(userEntity2);

    ExerciseRecordEntity exerciseRecord = getExerciseRecord(savedUser1, LocalDate.now());
    ExerciseRecordEntity savedRecord = exerciseRecordRepository.save(exerciseRecord);

    ExerciseRecordForm exerciseRecordForm =
        ExerciseRecordForm.builder().description("updated description").build();

    // when
    ExerciseRecordDomainDto recordDomainDto = apiUserExerciseService.updateExerciseRecord
        (savedUser1.getAuthId(), savedRecord.getId(), exerciseRecordForm);

    // then
    assertThat(recordDomainDto.getDescription()).isEqualTo(exerciseRecordForm.getDescription());
    assertThat(recordDomainDto.getUser()).isEqualTo(savedUser1);
    assertThat(recordDomainDto.getExerciseRecDt()).isEqualTo(LocalDate.now());
  }

  @Test
  @DisplayName("운동 정보 수정 - 실패 - 유저 정보 없음")
  void updateExerciseRecordTestFail_no_user() throws Exception {
    // given
    UserEntity userEntity1 = getUserEntity(1);
    UserEntity savedUser1 = userRepository.save(userEntity1);

    ExerciseRecordEntity exerciseRecord = getExerciseRecord(savedUser1, LocalDate.now());
    ExerciseRecordEntity savedRecord = exerciseRecordRepository.save(exerciseRecord);

    ExerciseRecordForm exerciseRecordForm =
        ExerciseRecordForm.builder().description("updated description").build();

    // when
    // then
    assertThatThrownBy(() ->
        apiUserExerciseService.updateExerciseRecord
            ("authId3", savedRecord.getId(), exerciseRecordForm)
    ).isInstanceOf(CustomException.class)
        .hasMessage(USER_NOT_FOUND.getMessage());
  }

  @Test
  @DisplayName("운동 정보 수정 - 실패 - 유저 정보 다름")
  void updateExerciseRecordTestFail_invalid_user() throws Exception {
    // given
    UserEntity userEntity1 = getUserEntity(1);
    UserEntity userEntity2 = getUserEntity(2);
    UserEntity savedUser1 = userRepository.save(userEntity1);
    UserEntity savedUser2 = userRepository.save(userEntity2);

    ExerciseRecordEntity exerciseRecord = getExerciseRecord(savedUser1, LocalDate.now());
    ExerciseRecordEntity savedRecord = exerciseRecordRepository.save(exerciseRecord);

    ExerciseRecordForm exerciseRecordForm =
        ExerciseRecordForm.builder().description("updated description").build();

    // when
    // then
    assertThatThrownBy(() ->
        apiUserExerciseService.updateExerciseRecord
            (savedUser2.getAuthId(), savedRecord.getId(), exerciseRecordForm)
    ).isInstanceOf(CustomException.class)
        .hasMessage(EXERCISE_RECORD_NOT_OWNED_USER.getMessage());
  }

  @Test
  @DisplayName("운동 정보 삭제 - 성공")
  void deleteExerciseRecordTest() throws Exception {
    // given
    UserEntity userEntity1 = getUserEntity(1);
    UserEntity savedUser1 = userRepository.save(userEntity1);

    ExerciseRecordEntity exerciseRecord = getExerciseRecord(savedUser1, LocalDate.now());
    ExerciseRecordEntity savedRecord = exerciseRecordRepository.save(exerciseRecord);


    // when
    apiUserExerciseService.deleteExerciseRecord(savedUser1.getAuthId(), savedRecord.getId());

    Optional<ExerciseRecordEntity> optionalExercise =
        exerciseRecordRepository.findById(savedRecord.getId());
    // then
    assertThat(optionalExercise).isEmpty();
    assertThat(savedUser1.getExerciseRecordEntityList().size()).isEqualTo(0);
  }

  @Test
  @DisplayName("운동 정보 삭제 - 실패 - 등록 당일이 아님")
  void deleteExerciseRecordTest_fail_not_current_day() throws Exception {
    // given
    UserEntity userEntity1 = getUserEntity(1);
    UserEntity savedUser1 = userRepository.save(userEntity1);

    ExerciseRecordEntity exerciseRecord =
        getExerciseRecord(savedUser1, LocalDate.now().minusDays(1));
    ExerciseRecordEntity savedRecord = exerciseRecordRepository.save(exerciseRecord);


    // when
    // then
    assertThatThrownBy(() -> apiUserExerciseService.deleteExerciseRecord
        (savedUser1.getAuthId(), savedRecord.getId())
    ).isInstanceOf(CustomException.class)
        .hasMessage(EXERCISE_RECORD_EXCEED_DELETE_DATE.getMessage());
  }

  @Test
  @DisplayName("운동 정보 삭제 - 실패 - 소유자가 다름")
  void deleteExerciseRecordTest_fail_invalid_user() throws Exception {
    // given
    UserEntity userEntity1 = getUserEntity(1);
    UserEntity userEntity2 = getUserEntity(2);
    UserEntity savedUser1 = userRepository.save(userEntity1);
    UserEntity savedUser2 = userRepository.save(userEntity2);

    ExerciseRecordEntity exerciseRecord = getExerciseRecord(savedUser1, LocalDate.now());
    ExerciseRecordEntity savedRecord = exerciseRecordRepository.save(exerciseRecord);


    // when
    // then
    assertThatThrownBy(() -> apiUserExerciseService.deleteExerciseRecord
        (savedUser2.getAuthId(), savedRecord.getId())
    ).isInstanceOf(CustomException.class)
        .hasMessage(EXERCISE_RECORD_NOT_OWNED_USER.getMessage());
  }


  private ExerciseRecordEntity getExerciseRecord(UserEntity userEntity, LocalDate date) {
    return ExerciseRecordEntity.builder()
        .exerciseRecDt(date)
        .description("description" + userEntity.getId())
        .user(userEntity)
        .build();
  }

  private UserEntity getUserEntity(int num) {
    return UserEntity.builder()
        .authId("authId" + num)
        .username("username" + num)
        .nickname(NicknameUtil.createRandomNickname())
        .role(RoleType.ROLE_USER)
        .birth(LocalDate.now())
        .gender(Gender.MALE)
        .exerciseDuration(0)
        .exerciseRecordEntityList(new ArrayList<>())
        .demerit(0)
        .build();
  }

}