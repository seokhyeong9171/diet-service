package com.health.domain.response;

import com.health.domain.dto.UserWeightDomainDto;
import com.health.domain.entity.BaseEntity;
import com.health.domain.entity.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserWeightResponse {

    private Double weight;

    private LocalDate weightRegDt;

    public static UserWeightResponse fromDomainDto(UserWeightDomainDto dto) {
        return UserWeightResponse.builder()
            .weight(dto.getWeight())
            .weightRegDt(dto.getWeightRegDt())
            .build();
    }
}
