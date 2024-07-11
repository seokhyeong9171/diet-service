package com.health.api.application;

import com.health.api.form.UserWeightForm;
import com.health.service.userservice.dto.UserWeightServiceDto;
import com.health.service.userservice.service.UserWeightService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserWeightApplication {

  private final UserWeightService userWeightService;

  public Page<UserWeightServiceDto> getUserWeightList(String authId, String scope, Pageable pageable) {
    return userWeightService.getUserWeightList(authId, scope, pageable);
  }

  public UserWeightServiceDto createWeightRecord(String authId, UserWeightForm form) {
    return userWeightService.createWeightRecord(authId, form.toDomainForm());
  }

  public UserWeightServiceDto updateWeightRecord(String authId, Long recordId, UserWeightForm form) {
    return userWeightService.updateWeightRecord(authId, recordId, form.toDomainForm());
  }

  public Long deleteWeightRecord(String authId, Long recordId) {
    return userWeightService.deleteWeightRecord(authId, recordId);
  }
}
