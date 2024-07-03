package com.health.common.util;

public class RedisKeyUtil {

  public static String intakeKey(String authId) {
    return "user_intake_calorie:" + authId;
  }

}
