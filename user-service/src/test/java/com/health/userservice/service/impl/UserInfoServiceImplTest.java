package com.health.userservice.service.impl;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import com.health.domain.dto.UserDomainDto;
import com.health.domain.entity.UserEntity;
import com.health.domain.form.UserDetailsDomainForm;
import com.health.domain.form.UserNicknameDomainForm;
import com.health.domain.repository.UserRepository;
import com.health.domain.type.Gender;
import com.health.domain.type.RoleType;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserInfoServiceImplTest {

  @InjectMocks
  private UserInfoServiceImpl userInfoService;

  @Mock
  private UserRepository userRepository;

  @Test
  @DisplayName("유저 정보 조회 - 성공")
  void getUserInfo() throws Exception {
      // given
    UserEntity userEntity = UserEntity.builder()
        .id(1L)
        .username("username")
        .nickname("nickname")
        .birth(LocalDate.now())
        .gender(Gender.MALE)
        .role(RoleType.ROLE_USER)
        .region(null)
        .height(123.12)
        .weight(123.123)
        .goalWeight(123.456)
        .exerciseDuration(1)
        .demerit(2)
        .build();

    given(userRepository.findByAuthId(anyString()))
        .willReturn(Optional.of(userEntity));

      // when
    UserDomainDto domainDto = userInfoService.getUserInfo("authId");

    // then
    assertThat(domainDto.getUsername()).isEqualTo(userEntity.getUsername());
    assertThat(domainDto.getNickname()).isEqualTo(userEntity.getNickname());
    assertThat(domainDto.getBirth()).isEqualTo(userEntity.getBirth());
    assertThat(domainDto.getGender()).isEqualTo(userEntity.getGender());
    assertThat(domainDto.getRole()).isEqualTo(userEntity.getRole());
    assertThat(domainDto.getRegion()).isEqualTo(userEntity.getRegion());
    assertThat(domainDto.getHeight()).isEqualTo(userEntity.getHeight());
    assertThat(domainDto.getWeight()).isEqualTo(userEntity.getWeight());
    assertThat(domainDto.getGoalWeight()).isEqualTo(userEntity.getGoalWeight());
    assertThat(domainDto.getExerciseDuration()).isEqualTo(userEntity.getExerciseDuration());
    assertThat(domainDto.getDemerit()).isEqualTo(userEntity.getDemerit());
  }

  @Test
  @DisplayName("유저 정보 업데이트 - 성공")
  void updateUserDetails() throws Exception {
    // given
    UserEntity userEntity = UserEntity.builder()
        .id(1L)
        .username("username")
        .nickname("nickname")
        .birth(LocalDate.now())
        .gender(Gender.MALE)
        .role(RoleType.ROLE_USER)
        .region(null)
        .weight(123.123)
        .goalWeight(123.456)
        .exerciseDuration(1)
        .demerit(2)
        .build();

    UserDetailsDomainForm updateForm = UserDetailsDomainForm.builder()
        .height(567.567)
        .weight(12.12)
        .goalWeight(12.34)
        .region(null)
        .build();

    given(userRepository.findByAuthId(anyString()))
        .willReturn(Optional.of(userEntity));

    // when
    UserDomainDto domainDto = userInfoService.updateUserDetails("authId", updateForm);

    // then
    assertThat(domainDto.getHeight()).isEqualTo(updateForm.getHeight());
    assertThat(domainDto.getWeight()).isEqualTo(updateForm.getWeight());
    assertThat(domainDto.getGoalWeight()).isEqualTo(updateForm.getGoalWeight());
    assertThat(domainDto.getRegion()).isEqualTo(updateForm.getRegion());

    assertThat(userEntity.getHeight()).isEqualTo(updateForm.getHeight());
    assertThat(userEntity.getWeight()).isEqualTo(updateForm.getWeight());
    assertThat(userEntity.getGoalWeight()).isEqualTo(updateForm.getGoalWeight());
    assertThat(userEntity.getRegion()).isEqualTo(updateForm.getRegion());

    assertThat(domainDto.getUsername()).isEqualTo(userEntity.getUsername());
    assertThat(domainDto.getNickname()).isEqualTo(userEntity.getNickname());
    assertThat(domainDto.getBirth()).isEqualTo(userEntity.getBirth());
    assertThat(domainDto.getGender()).isEqualTo(userEntity.getGender());
    assertThat(domainDto.getRole()).isEqualTo(userEntity.getRole());
    assertThat(domainDto.getRegion()).isEqualTo(userEntity.getRegion());
    assertThat(domainDto.getWeight()).isEqualTo(userEntity.getWeight());
    assertThat(domainDto.getGoalWeight()).isEqualTo(userEntity.getGoalWeight());
    assertThat(domainDto.getExerciseDuration()).isEqualTo(userEntity.getExerciseDuration());
    assertThat(domainDto.getDemerit()).isEqualTo(userEntity.getDemerit());
  }

  @Test
  @DisplayName("유저 닉네임 업데이트 - 성공")
  void updateUserNickname() throws Exception {
      // given
    UserEntity userEntity = UserEntity.builder()
        .authId("authId")
        .nickname("nickname1")
        .build();

    UserNicknameDomainForm updateForm =
        UserNicknameDomainForm.builder().nickname("nickname2").build();

    given(userRepository.findByAuthId(anyString()))
        .willReturn(Optional.of(userEntity));

    // when
    String updatedNickname = userInfoService.updateUserNickname("authId", updateForm);

    // then
    assertThat(updatedNickname).isEqualTo(updateForm.getNickname());

    assertThat(userEntity.getNickname()).isEqualTo(updatedNickname);
  }
}