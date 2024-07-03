package com.health.common.util;

import java.time.LocalDate;

public class RedisKeyUtil {

  public static String intakeKey(String authId, LocalDate date) {
    return "intake:" + date + ":" + authId;
  }

}
