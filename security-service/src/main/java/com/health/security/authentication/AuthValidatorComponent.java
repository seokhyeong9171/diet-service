package com.health.security.authentication;

import static com.health.common.exception.ErrorCode.USER_INVALID_ACCESS;

import com.health.common.exception.CustomException;
import com.health.security.jwt.JwtComponent;
import jakarta.servlet.http.Cookie;
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
