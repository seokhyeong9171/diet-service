package com.health.api.service;

import static com.health.common.exception.ErrorCode.WEIGHT_RECORD_ALREADY_POSTED;
import static com.health.common.exception.ErrorCode.WEIGHT_RECORD_NOT_OWNED_USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.health.api.form.UserWeightForm;
import com.health.common.exception.CustomException;
import com.health.domain.dto.UserWeightDomainDto;
import com.health.domain.entity.UserEntity;
import com.health.domain.entity.UserWeightEntity;
import com.health.domain.repository.UserRepository;
import com.health.domain.repository.UserWeightRepository;
import java.time.LocalDate;
import java.util.ArrayList;
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
class ApiUserWeightServiceTest {

  @Autowired ApiUserWeightService apiUserWeightService;
  @Autowired UserRepository userRepository;
  @Autowired UserWeightRepository userWeightRepository;

  @Test
  @DisplayName("몸무게 정보 조회 - 성공 - day")
  void getUserWeightList_day() throws Exception {
      // given
    UserEntity userEntity = getUserEntity(1);
    UserEntity userEntity2 = getUserEntity(2);
    UserEntity savedUser = userRepository.save(userEntity);
    UserEntity savedUser2 = userRepository.save(userEntity2);

    UserWeightEntity weightEntity1 = getWeightEntity(savedUser, 2024, 6, 22);
    UserWeightEntity weightEntity2 = getWeightEntity(savedUser, 2024, 6, 23);
    UserWeightEntity weightEntity3 = getWeightEntity(savedUser2, 2024, 6, 21);
    UserWeightEntity weightEntity4 = getWeightEntity(savedUser, 2024, 6, 24);
    UserWeightEntity savedWeight1 = userWeightRepository.save(weightEntity1);
    UserWeightEntity savedWeight2 = userWeightRepository.save(weightEntity2);
    UserWeightEntity savedWeight3 = userWeightRepository.save(weightEntity3);
    UserWeightEntity savedWeight4 = userWeightRepository.save(weightEntity4);

    Pageable pageable = PageRequest.of(0, 10);

    // when
    Page<UserWeightDomainDto> userWeightList =
        apiUserWeightService.getUserWeightList(savedUser.getAuthId(), "day", pageable);

    // then
    assertThat(userWeightList.getTotalElements()).isEqualTo(3);
    assertThat(userWeightList.getContent().getFirst().getWeightRegDt())
        .isEqualTo(LocalDate.of(2024, 6, 24));
    assertThat(userWeightList.getContent().getLast().getWeightRegDt())
        .isEqualTo(LocalDate.of(2024, 6, 22));
  }

  @Test
  @DisplayName("몸무게 정보 조회 - 성공 - week")
  void getUserWeightList_week() throws Exception {
    // given
    UserEntity userEntity = getUserEntity(1);
    UserEntity userEntity2 = getUserEntity(2);
    UserEntity savedUser = userRepository.save(userEntity);
    UserEntity savedUser2 = userRepository.save(userEntity2);

    UserWeightEntity weightEntity1 = getWeightEntity(savedUser, 2024, 6, 22);
    UserWeightEntity weightEntity2 = getWeightEntity(savedUser, 2024, 6, 23);
    UserWeightEntity weightEntity3 = getWeightEntity(savedUser2, 2024, 6, 21);
    UserWeightEntity weightEntity4 = getWeightEntity(savedUser, 2024, 6, 24);
    UserWeightEntity savedWeight1 = userWeightRepository.save(weightEntity1);
    UserWeightEntity savedWeight2 = userWeightRepository.save(weightEntity2);
    UserWeightEntity savedWeight3 = userWeightRepository.save(weightEntity3);
    UserWeightEntity savedWeight4 = userWeightRepository.save(weightEntity4);

    Pageable pageable = PageRequest.of(0, 10);

    // when
    Page<UserWeightDomainDto> userWeightList =
        apiUserWeightService.getUserWeightList(savedUser.getAuthId(), "week", pageable);

    // then
    assertThat(userWeightList.getTotalElements()).isEqualTo(1);
    assertThat(userWeightList.getContent().getFirst().getWeightRegDt())
        .isEqualTo(LocalDate.of(2024, 6, 23));
  }

  @Test
  @DisplayName("몸무게 정보 조회 - 성공 - month")
  void getUserWeightList_month() throws Exception {
    // given
    UserEntity userEntity = getUserEntity(1);
    UserEntity savedUser = userRepository.save(userEntity);

    UserWeightEntity weightEntity1 = getWeightEntity(savedUser, 2024, 6, 22);
    UserWeightEntity weightEntity2 = getWeightEntity(savedUser, 2024, 5, 31);
    UserWeightEntity weightEntity3 = getWeightEntity(savedUser, 2024, 5, 30);
    UserWeightEntity weightEntity4 = getWeightEntity(savedUser, 2024, 6, 24);
    UserWeightEntity savedWeight1 = userWeightRepository.save(weightEntity1);
    UserWeightEntity savedWeight2 = userWeightRepository.save(weightEntity2);
    UserWeightEntity savedWeight3 = userWeightRepository.save(weightEntity3);
    UserWeightEntity savedWeight4 = userWeightRepository.save(weightEntity4);

    Pageable pageable = PageRequest.of(0, 10);

    // when
    Page<UserWeightDomainDto> userWeightList =
        apiUserWeightService.getUserWeightList(savedUser.getAuthId(), "month", pageable);

    // then
    assertThat(userWeightList.getTotalElements()).isEqualTo(1);
    assertThat(userWeightList.getContent().getFirst().getWeightRegDt())
        .isEqualTo(LocalDate.of(2024, 5, 31));
  }

  @Test
  @DisplayName("몸무게 정보 등록 - 성공")
  void createUserWeight() throws Exception {
      // given
    UserEntity userEntity = getUserEntity(1);
    UserEntity savedUser = userRepository.save(userEntity);

    UserWeightForm weightForm = UserWeightForm.builder().weight(123.123).build();

    // when
    UserWeightDomainDto weightRecord =
        apiUserWeightService.createWeightRecord(savedUser.getAuthId(), weightForm);

    // then
    assertThat(weightRecord.getWeightRegDt()).isEqualTo(LocalDate.now());
    assertThat(weightRecord.getUser()).isEqualTo(savedUser);
    assertThat(weightRecord.getWeight()).isEqualTo(weightForm.getWeight());
    assertThat(weightRecord.getId()).isNotNull();

    assertThat(savedUser.getUserWeightList().size()).isEqualTo(1);
  }

  @Test
  @DisplayName("몸무게 정보 등록 - 실패 - 오늘 이미 등록")
  void createUserWeight_fail_already_post_today() throws Exception {
    // given
    UserEntity userEntity = getUserEntity(1);
    UserEntity savedUser = userRepository.save(userEntity);

    UserWeightEntity weightEntity = getWeightEntity(savedUser, LocalDate.now());
    userWeightRepository.save(weightEntity);

    UserWeightForm weightForm = UserWeightForm.builder().weight(123.123).build();

    // when
    // then
    assertThatThrownBy(() -> apiUserWeightService.createWeightRecord
        (savedUser.getAuthId(), weightForm)
    ).isInstanceOf(CustomException.class).hasMessage(WEIGHT_RECORD_ALREADY_POSTED.getMessage());
  }

  @Test
  @DisplayName("몸무게 정보 수정 - 성공")
  void updateUserWeight() throws Exception {
      // given
    UserEntity userEntity = getUserEntity(1);
    UserEntity savedUser = userRepository.save(userEntity);

    UserWeightEntity weightEntity = getWeightEntity(savedUser, LocalDate.now());
    UserWeightEntity savedWeight = userWeightRepository.save(weightEntity);

    UserWeightForm weightForm = UserWeightForm.builder().weight(321.321).build();

      // when
    UserWeightDomainDto userWeightDomainDto =
        apiUserWeightService.updateWeightRecord(savedUser.getAuthId(), savedWeight.getId(), weightForm);

    // then
    assertThat(userWeightDomainDto.getWeight()).isEqualTo(weightForm.getWeight());
  }

  @Test
  @DisplayName("몸무게 정보 수정 - 실패 - 해당 유저의 기록 아님")
  void updateUserWeight_fail_invalid_user() throws Exception {
    // given
    UserEntity userEntity = getUserEntity(1);
    UserEntity userEntity2 = getUserEntity(2);
    UserEntity savedUser = userRepository.save(userEntity);
    UserEntity savedUser2 = userRepository.save(userEntity2);

    UserWeightEntity weightEntity = getWeightEntity(savedUser, LocalDate.now());
    UserWeightEntity savedWeight = userWeightRepository.save(weightEntity);

    UserWeightForm weightForm = UserWeightForm.builder().weight(321.321).build();

    // when
    // then

    assertThatThrownBy(() -> apiUserWeightService.updateWeightRecord
        (savedUser2.getAuthId(), savedWeight.getId(), weightForm)
    ).isInstanceOf(CustomException.class)
        .hasMessage(WEIGHT_RECORD_NOT_OWNED_USER.getMessage());
  }

  @Test
  @DisplayName("몸무게 정보 삭제 - 성공")
  void deleteUserWeight() throws Exception {
    // given
    UserEntity userEntity = getUserEntity(1);
    UserEntity savedUser = userRepository.save(userEntity);

    UserWeightForm weightForm = UserWeightForm.builder().weight(321.321).build();

    UserWeightDomainDto weightRecord =
        apiUserWeightService.createWeightRecord(savedUser.getAuthId(), weightForm);

    // when
    Long id = apiUserWeightService.deleteWeightRecord(savedUser.getAuthId(), weightRecord.getId());

    // then
    assertThat(userWeightRepository.findById(id)).isEmpty();
    assertThat(savedUser.getUserWeightList().size()).isEqualTo(0);
  }

  @Test
  @DisplayName("몸무게 정보 삭제 - 실패 - 해당 유저의 기록 아님")
  void deleteUserWeight_fail_invalid_user() throws Exception {
    // given
    UserEntity userEntity = getUserEntity(1);
    UserEntity userEntity2 = getUserEntity(2);
    UserEntity savedUser = userRepository.save(userEntity);
    UserEntity savedUser2 = userRepository.save(userEntity2);

    UserWeightEntity weightEntity = getWeightEntity(savedUser, LocalDate.now());
    UserWeightEntity savedWeight = userWeightRepository.save(weightEntity);


    // when
    // then
    assertThatThrownBy(() -> apiUserWeightService.deleteWeightRecord
        (savedUser2.getAuthId(), savedWeight.getId())
    ).isInstanceOf(CustomException.class)
        .hasMessage(WEIGHT_RECORD_NOT_OWNED_USER.getMessage());
  }




  private UserWeightEntity getWeightEntity(UserEntity user, LocalDate date) {
    return UserWeightEntity.builder()
        .user(user).weightRegDt(date)
        .weight(123.123).build();
  }

  private UserWeightEntity getWeightEntity(UserEntity user, int year, int month, int day) {
    LocalDate localDate = LocalDate.of(year, month, day);
    return getWeightEntity(user, localDate);
  }

  private UserEntity getUserEntity(int num) {
    return UserEntity.builder()
        .authId("authId" + num)
        .username("username" + num)
        .exerciseDuration(0)
        .userWeightList(new ArrayList<>())
        .demerit(0)
        .build();
  }

}