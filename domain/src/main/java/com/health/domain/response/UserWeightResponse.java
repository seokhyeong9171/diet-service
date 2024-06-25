package com.health.domain.response;

import com.health.domain.dto.UserWeightDomainDto;
import java.time.LocalDate;
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
