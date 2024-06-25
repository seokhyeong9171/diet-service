package com.health.security.oauth2;

import static com.health.security.constant.SecurityConstant.AUTHORIZATION_COOKIE_NAME;

import com.health.security.dto.CustomOAuth2User;
import com.health.security.jwt.JwtComponent;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

  private final JwtComponent jwtComponent;

  @Override
  public void onAuthenticationSuccess(
      HttpServletRequest request, HttpServletResponse response, Authentication authentication
  ) throws IOException, ServletException {

    CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

    String authId = oAuth2User.getAuthId();
    String role = oAuth2User.getAuthorities().iterator().next().getAuthority();

    String jwtToken = jwtComponent.createToken(authId, role);

    Cookie cookie = createCookie(AUTHORIZATION_COOKIE_NAME, jwtToken);

    response.addCookie(cookie);
  }

  private Cookie createCookie(String key, String value) {
    Cookie cookie = new Cookie(key, value);
    cookie.setMaxAge(60*60*60);
    cookie.setPath("/");
    cookie.setHttpOnly(true);
    return cookie;
  }
}
