package com.health.common.redis;

import java.time.LocalDate;
import org.springframework.stereotype.Component;

@Component
public class RedisKeyComponent {

  public String intakeKey(String authId, LocalDate date) {
    return "intake:" + date + ":" + authId;
  }

}
