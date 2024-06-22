package com.health.security.response;

import com.health.domain.type.Gender;
import java.time.LocalDate;

public interface OAuth2Response {

  String getProvider();

  String getProviderId();

  String getAuthId();

  String getName();

  LocalDate getBirth();

  Gender getGender();

}
