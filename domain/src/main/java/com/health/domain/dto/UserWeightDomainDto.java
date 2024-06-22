package com.health.domain.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserWeightDomainDto {

    private Long id;

    private UserDomainDto user;

    private Double weight;

    private LocalDate weightRegDt;

}
