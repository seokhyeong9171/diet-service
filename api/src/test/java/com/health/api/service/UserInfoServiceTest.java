package com.health.api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.health.api.form.UserDetailsForm;
import com.health.api.form.UserNicknameForm;
import com.health.common.exception.CustomException;
import com.health.common.exception.ErrorCode;
import com.health.domain.dto.UserDomainDto;
import com.health.domain.entity.UserEntity;
import com.health.domain.repository.UserRepository;
import com.health.domain.type.Gender;
import com.health.domain.type.Region;
import com.health.domain.type.RoleType;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ComponentScan("com.health")
@Transactional
@EntityScan(basePackages = "com.health.domain")
class UserInfoServiceTest {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private UserInfoApplication userInfoApplication;

  @Test
  @DisplayName("유저 정보 조회 - 성공")
  void getUserInfoTest() throws Exception {
    // given
    String authId1 = "authId1";
    UserEntity userEntity1 = getUserEntity(1);
    UserEntity userEntity2 = getUserEntity(2);

    UserEntity saved1 = userRepository.save(userEntity1);
    UserEntity saved2 = userRepository.save(userEntity2);

    // when
    UserDomainDto domainDto = userInfoApplication.getUserInfo(authId1);

    // then
    assertThat(domainDto.getId()).isEqualTo(saved1.getId());
    assertThat(domainDto.getAuthId()).isEqualTo(userEntity1.getAuthId());
    assertThat(domainDto.getNickname()).isEqualTo(userEntity1.getNickname());
    assertThat(domainDto.getUsername()).isEqualTo(userEntity1.getUsername());
    assertThat(domainDto.getBirth()).isEqualTo(userEntity1.getBirth());
    assertThat(domainDto.getGender()).isEqualTo(userEntity1.getGender());
    assertThat(domainDto.getRole()).isEqualTo(userEntity1.getRole());
    assertThat(domainDto.getRegion()).isEqualTo(userEntity1.getRegion());
    assertThat(domainDto.getHeight()).isEqualTo(userEntity1.getHeight());
    assertThat(domainDto.getWeight()).isEqualTo(userEntity1.getWeight());
    assertThat(domainDto.getGoalWeight()).isEqualTo(userEntity1.getGoalWeight());
    assertThat(domainDto.getExerciseDuration()).isEqualTo(userEntity1.getExerciseDuration());
    assertThat(domainDto.getDemerit()).isEqualTo(userEntity1.getDemerit());

  }

  @Test
  @DisplayName("유저 정보 업데이트 - 성공")
  void updateUserDetailsTest() throws Exception {
    // given
    UserEntity userEntity = getUserEntity(1);
    UserDetailsForm userDetailsForm = UserDetailsForm.builder()
        .height(123.123)
        .weight(456.456)
        .goalWeight(321.321)
        .region(Region.JEJUDO)
        .build();

    UserEntity savedUser = userRepository.save(userEntity);

    // when
    UserDomainDto domainDto =
        userInfoApplication.updateUserInfo(savedUser.getAuthId(), userDetailsForm);

    // then
    assertThat(domainDto.getHeight()).isEqualTo(userDetailsForm.getHeight());
    assertThat(domainDto.getWeight()).isEqualTo(userDetailsForm.getWeight());
    assertThat(domainDto.getGoalWeight()).isEqualTo(userDetailsForm.getGoalWeight());
    assertThat(domainDto.getRegion()).isEqualTo(userDetailsForm.getRegion());

    assertThat(domainDto.getId()).isEqualTo(savedUser.getId());
    assertThat(domainDto.getAuthId()).isEqualTo(savedUser.getAuthId());
    assertThat(domainDto.getNickname()).isEqualTo(savedUser.getNickname());
    assertThat(domainDto.getUsername()).isEqualTo(savedUser.getUsername());
    assertThat(domainDto.getBirth()).isEqualTo(savedUser.getBirth());
    assertThat(domainDto.getGender()).isEqualTo(savedUser.getGender());
    assertThat(domainDto.getRole()).isEqualTo(savedUser.getRole());
    assertThat(domainDto.getExerciseDuration()).isEqualTo(savedUser.getExerciseDuration());
    assertThat(domainDto.getDemerit()).isEqualTo(savedUser.getDemerit());
  }

  @Test
  @DisplayName("유저 정보 업데이트 - 실패 - authId 다름")
  void updateUserDetailsTest_fail_authId() throws Exception {
    // given
    UserEntity userEntity = getUserEntity(1);
    UserDetailsForm userDetailsForm = UserDetailsForm.builder()
        .height(123.123)
        .weight(456.456)
        .goalWeight(321.321)
        .region(Region.JEJUDO)
        .build();

    UserEntity savedUser = userRepository.save(userEntity);

    // when
    // then
    assertThatThrownBy(() -> userInfoApplication.updateUserInfo("authId2", userDetailsForm))
        .isInstanceOf(CustomException.class)
        .hasMessage(ErrorCode.USER_NOT_FOUND.getMessage());
  }

  @Test
  @DisplayName("유저 닉네임 업데이트 - 성공")
  void updateUserNicknameTest() throws Exception {
    // given
    UserEntity userEntity = getUserEntity(1);
    UserNicknameForm userNicknameForm = UserNicknameForm.builder()
        .nickname("changedNickname")
        .build();

    UserEntity savedUser = userRepository.save(userEntity);

    // when
    String updatedNickname =
        userInfoApplication.updateUserNickname(savedUser.getAuthId(), userNicknameForm);

    // then
    assertThat(updatedNickname).isEqualTo(userNicknameForm.getNickname());
  }

  @Test
  @DisplayName("유저 닉네임 업데이트 - 실패 - authId 다름")
  void updateUserNicknameTest_fail_authId() throws Exception {
    // given
    UserEntity userEntity = getUserEntity(1);
    UserDetailsForm userDetailsForm = UserDetailsForm.builder()
        .height(123.123)
        .weight(456.456)
        .goalWeight(321.321)
        .region(Region.JEJUDO)
        .build();

    UserEntity savedUser = userRepository.save(userEntity);

    // when
    // then
    assertThatThrownBy(() -> userInfoApplication.updateUserInfo("authId2", userDetailsForm))
        .isInstanceOf(CustomException.class)
        .hasMessage(ErrorCode.USER_NOT_FOUND.getMessage());
  }

  @Test
  @DisplayName("유저 닉네임 업데이트 - 실패 - 닉네임 중복")
  void updateUserNicknameTest_fail_duplicated_nickname() throws Exception {
    // given
    UserEntity userEntity = getUserEntity(1);
    UserEntity userEntity2 = getUserEntity(2);

    UserNicknameForm userNicknameForm =
        UserNicknameForm.builder().nickname(userEntity2.getNickname()).build();

    UserEntity savedUser = userRepository.save(userEntity);
    UserEntity savedUser2 = userRepository.save(userEntity2);

    // when
    // then
    assertThatThrownBy(() -> userInfoApplication.updateUserNickname(savedUser.getAuthId(), userNicknameForm))
        .isInstanceOf(CustomException.class)
        .hasMessage(ErrorCode.NICKNAME_DUPLICATED.getMessage());
  }




  private UserEntity getUserEntity(int id) {
    return UserEntity.builder()
        .authId("authId" + id)
        .username("username" + id)
        .nickname("nickname" + id)
        .birth(LocalDate.now())
        .gender(Gender.MALE)
        .role(RoleType.ROLE_USER)
        .region(Region.SEOUL)
        .height((double) (id + 100))
        .weight((double) (id + 10))
        .goalWeight((double) (id + 5))
        .exerciseDuration(id)
        .demerit(id)
        .build();

  }
}
