package com.health.dto;

import com.health.type.Gender;
import com.health.type.Region;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDomainDto {

    private Long id;

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

    private List<UserWeightDomainDto> userWeightList;

    private List<ExerciseRecordDomainDto> exerciseRecordDomainDtoList;

    private List<PostDomainDto> postList;


}
