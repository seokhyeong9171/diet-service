package com.health.domain.dto;

import com.health.domain.entity.UserEntity;
import com.health.domain.entity.UserWeightEntity;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserWeightDomainDto {

    private Long id;

    private UserEntity user;

    private Double weight;

    private LocalDate weightRegDt;


    public static UserWeightDomainDto fromEntity(UserWeightEntity userWeightEntity) {
        return UserWeightDomainDto.builder()
            .id(userWeightEntity.getId())
            .user(userWeightEntity.getUser())
            .weight(userWeightEntity.getWeight())
            .weightRegDt(userWeightEntity.getWeightRegDt())
            .build();
    }
}
