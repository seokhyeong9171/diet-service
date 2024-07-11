package com.health.userservice.type;

import com.health.domain.exception.CustomException;
import com.health.domain.exception.ErrorCode;
import java.util.Arrays;
import lombok.Getter;

@Getter
public enum Scope {

  DAY("day"),
  WEEK("week"),
  MONTH("month");

  private final String value;

  Scope(String value) {
    this.value = value;
  }

  public static Scope fromValue(String value) {
    return Arrays.stream(Scope.values())
        .filter(s -> s.value.equals(value))
        .findFirst()
        .orElseThrow(() -> new CustomException(ErrorCode.PARAMETER_INVALID));
  }
}
