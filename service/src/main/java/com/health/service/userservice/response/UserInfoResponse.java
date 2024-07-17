package com.health.service.userservice.response;

import com.health.domain.type.Gender;
import com.health.domain.type.Region;
import com.health.service.userservice.dto.UserServiceDto;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfoResponse {

  private String username;
  private String nickname;

  private LocalDate birth;

  private Gender gender;

  private Double height;

  private Double weight;

  private Double goalWeight;
  private Region region;
  private Integer exerciseDuration;

  private Integer demerit;

  public static UserInfoResponse fromDomainDto(UserServiceDto dto) {
    return UserInfoResponse.builder()
        .username(dto.getUsername())
        .nickname(dto.getNickname())
        .birth(dto.getBirth())
        .gender(dto.getGender())
        .height(dto.getHeight())
        .weight(dto.getWeight())
        .goalWeight(dto.getGoalWeight())
        .region(dto.getRegion())
        .exerciseDuration(dto.getExerciseDuration())
        .demerit(dto.getDemerit())
        .build();
  }

}
