package com.health.service.userservice.dto;

import com.health.domain.entity.UserEntity;
import com.health.domain.entity.UserWeightEntity;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserWeightServiceDto {

    private Long id;

    private UserEntity user;

    private Double weight;

    private LocalDate weightRegDt;


    public static UserWeightServiceDto fromEntity(UserWeightEntity userWeightEntity) {
        return UserWeightServiceDto.builder()
            .id(userWeightEntity.getId())
            .user(userWeightEntity.getUser())
            .weight(userWeightEntity.getWeight())
            .weightRegDt(userWeightEntity.getWeightRegDt())
            .build();
    }
}
