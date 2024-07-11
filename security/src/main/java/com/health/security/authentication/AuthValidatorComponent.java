package com.health.security.authentication;


import static com.health.domain.exception.ErrorCode.*;

import com.health.domain.exception.CustomException;
import com.health.security.jwt.JwtComponent;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class AuthValidatorComponent {

  private final JwtComponent jwtComponent;

  public String validateAuthId(String jwt) {

    String authId = jwtComponent.getAuthId(jwt);

    String principal = (String) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();

    if (!StringUtils.hasText(principal) || !principal.equals(authId)) {
      throw new CustomException(USER_INVALID_ACCESS);
    }

    return authId;
  }

}
