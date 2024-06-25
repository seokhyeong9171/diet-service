package com.health.domain.dto;

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
public class ExerciseRecordDomainDto {

    private Long id;

    private LocalDate exerciseRecDt;

    private String description;

    private UserEntity user;

    public static ExerciseRecordDomainDto fromEntity(ExerciseRecordEntity exerciseRecordEntity) {
        return ExerciseRecordDomainDto.builder()
            .id(exerciseRecordEntity.getId())
            .exerciseRecDt(exerciseRecordEntity.getExerciseRecDt())
            .description(exerciseRecordEntity.getDescription())
            .user(exerciseRecordEntity.getUser())
            .build();
    }

}
