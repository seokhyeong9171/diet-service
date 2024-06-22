package com.health.security.response.impl;

import com.health.security.response.OAuth2Response;
import com.health.domain.type.Gender;
import java.time.LocalDate;
import java.util.Map;


public class NaverOAuth2Response implements OAuth2Response {

  private final Map<String, Object> attribute;

  public NaverOAuth2Response(Map<String, Object> attribute) {
    this.attribute = (Map<String, Object>) attribute.get("response");
  }

  @Override
  public String getProvider() {
    return "naver";
  }

  @Override
  public String getProviderId() {
    return attribute.get("id").toString();
  }

  @Override
  public String getAuthId() {
    return getProvider() + "_" + getProviderId();
  }

  @Override
  public String getName() {
    return attribute.get("name").toString();
  }

  @Override
  public LocalDate getBirth() {

    int[] birthMonthAndDay = getBirthMonthAndDay();

    int year = getBirthYear();
    int month = birthMonthAndDay[0];
    int day = birthMonthAndDay[1];

    return LocalDate.of(year, month, day);
  }

  @Override
  public Gender getGender() {
    String genderValue = attribute.get("gender").toString();

    if (genderValue.equals("F")) {
      return Gender.FEMALE;

    } else if (genderValue.equals("M")) {
      return Gender.MALE;

    } else {
      return Gender.UNDEFINED;
    }
  }




  private int getBirthYear() {
    return Integer.parseInt(attribute.get("birthyear").toString());
  }

  private int[] getBirthMonthAndDay() {
    String[] split = this.attribute.get("birthday").toString().split("-");

    return new int[]{
        Integer.parseInt(split[0]), Integer.parseInt(split[1])
    };
  }
}
