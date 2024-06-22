package com.health.security.authentication;

import static com.health.common.exception.ErrorCode.*;

import com.health.common.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class AuthValidatorComponent {

  public void validateAuthId(String authId) {
    String principal = (String) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();

    if (!StringUtils.hasText(principal) || !principal.equals(authId)) {
      throw new CustomException(USER_INVALID_ACCESS);
    }

  }

}
