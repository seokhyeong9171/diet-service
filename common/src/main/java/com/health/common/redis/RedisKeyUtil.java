package com.health.common.redis;

import java.time.LocalDate;

public class RedisKeyUtil {

  public static String intakeKey(String authId, LocalDate date) {
    return "intake:" + date + ":" + authId;
  }

}
