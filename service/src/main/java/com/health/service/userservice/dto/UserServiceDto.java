package com.health.service.userservice.dto;

import com.health.domain.entity.UserEntity;
import com.health.domain.type.Gender;
import com.health.domain.type.Region;
import com.health.domain.type.RoleType;
import com.health.service.forumservice.dto.PostServiceDto;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserServiceDto {

    private Long id;

    private String authId;

    private String username;
    private String nickname;

    private RoleType role;

    private LocalDate birth;

    private Gender gender;

    private Double height;

    private Double weight;
    private Double goalWeight;
    private Region region;
    private Integer exerciseDuration;

    private Integer demerit;

    private List<UserWeightServiceDto> userWeightList;

    private List<ExerciseRecordServiceDto> exerciseRecordServiceDtoList;

    private List<PostServiceDto> postList;

    public static UserServiceDto fromEntity(UserEntity userEntity) {
        return UserServiceDto.builder()
            .id(userEntity.getId())
            .authId(userEntity.getAuthId())
            .username(userEntity.getUsername())
            .nickname(userEntity.getNickname())
            .role(userEntity.getRole())
            .birth(userEntity.getBirth())
            .gender(userEntity.getGender())
            .height(userEntity.getHeight())
            .weight(userEntity.getWeight())
            .goalWeight(userEntity.getGoalWeight())
            .region(userEntity.getRegion())
            .exerciseDuration(userEntity.getExerciseDuration())
            .demerit(userEntity.getDemerit())
            .build();
    }
}
