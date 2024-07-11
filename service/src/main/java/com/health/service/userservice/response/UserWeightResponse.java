package com.health.service.userservice.response;

import com.health.service.userservice.dto.UserWeightServiceDto;
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

    public static UserWeightResponse fromDomainDto(UserWeightServiceDto dto) {
        return UserWeightResponse.builder()
            .weight(dto.getWeight())
            .weightRegDt(dto.getWeightRegDt())
            .build();
    }
}
