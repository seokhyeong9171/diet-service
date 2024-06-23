package com.health.userservice.service;

import com.health.domain.dto.UserWeightDomainDto;
import com.health.domain.form.UserWeightDomainForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserWeightService {

  Page<UserWeightDomainDto> getUserWeightList(String authId, String scope, Pageable pageable);

  UserWeightDomainDto createWeightRecord(String authId, UserWeightDomainForm form);

  UserWeightDomainDto updateWeightRecord(String authId, Long recordId, UserWeightDomainForm form);

  Long deleteWeightRecord(String authId, Long recordId);
}
