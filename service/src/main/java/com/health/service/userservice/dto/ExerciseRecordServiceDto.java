package com.health.service.userservice.dto;

import com.health.domain.entity.ExerciseRecordEntity;
import com.health.domain.entity.UserEntity;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExerciseRecordServiceDto {

    private Long id;

    private LocalDate exerciseRecDt;

    private String description;

    private UserEntity user;

    public static ExerciseRecordServiceDto fromEntity(ExerciseRecordEntity exerciseRecordEntity) {
        return ExerciseRecordServiceDto.builder()
            .id(exerciseRecordEntity.getId())
            .exerciseRecDt(exerciseRecordEntity.getExerciseRecDt())
            .description(exerciseRecordEntity.getDescription())
            .user(exerciseRecordEntity.getUser())
            .build();
    }

}
