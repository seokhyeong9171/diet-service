package com.health.userservice.service;

import com.health.userservice.dto.UserWeightServiceDto;
import com.health.userservice.form.UserWeightServiceForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserWeightService {

  Page<UserWeightServiceDto> getUserWeightList(String authId, String scope, Pageable pageable);

  UserWeightServiceDto createWeightRecord(String authId, UserWeightServiceForm serviceForm);

  UserWeightServiceDto updateWeightRecord(String authId, Long recordId, UserWeightServiceForm serviceForm);

  Long deleteWeightRecord(String authId, Long recordId);


}
