package com.health.api.service;

import com.health.api.form.UserWeightForm;
import com.health.domain.dto.UserWeightDomainDto;
import com.health.userservice.service.UserWeightService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserWeightApplication {

  private final UserWeightService userWeightService;

  public Page<UserWeightDomainDto> getUserWeightList(String authId, String scope, Pageable pageable) {
    return userWeightService.getUserWeightList(authId, scope, pageable);
  }

  public UserWeightDomainDto createWeightRecord(String authId, UserWeightForm form) {
    return userWeightService.createWeightRecord(authId, form.toDomainForm());
  }

  public UserWeightDomainDto updateWeightRecord(String authId, Long recordId, UserWeightForm form) {
    return userWeightService.updateWeightRecord(authId, recordId, form.toDomainForm());
  }

  public Long deleteWeightRecord(String authId, Long recordId) {
    return userWeightService.deleteWeightRecord(authId, recordId);
  }
}
