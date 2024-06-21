package com.health.dto;

import com.health.entity.UserEntity;
import com.health.type.Gender;
import com.health.type.Region;
import com.health.type.RoleType;
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
public class UserDomainDto {

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

    private List<UserWeightDomainDto> userWeightList;

    private List<ExerciseRecordDomainDto> exerciseRecordDomainDtoList;

    private List<PostDomainDto> postList;

}
