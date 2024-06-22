package com.health.security.jwt;

import static com.health.security.constant.SecurityConstant.*;

import com.health.security.dto.CustomOAuth2User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

  private final JwtComponent jwtComponent;


  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String jwtToken = getJwtTokenFromCookie(request);

    if (jwtToken == null) {
      filterChain.doFilter(request, response);

      return;
    }

    if (jwtComponent.isExpired(jwtToken)) {
      filterChain.doFilter(request, response);

      return;
    }

    String authId = jwtComponent.getAuthId(jwtToken);
    String role = jwtComponent.getRole(jwtToken);

    CustomOAuth2User customOAuth2User = CustomOAuth2User.forAuthentication(authId, role);

    Authentication authentication = getAuthentication(customOAuth2User);

    SecurityContextHolder.getContext().setAuthentication(authentication);

    filterChain.doFilter(request, response);
  }

  private String getJwtTokenFromCookie(HttpServletRequest request) {
    return Arrays.stream(request.getCookies())
        .filter(cookie -> cookie.getName().equals(AUTHORIZATION_COOKIE_NAME))
        .map(Cookie::getValue)
        .findAny()
        .orElse(null);
  }


  private UsernamePasswordAuthenticationToken getAuthentication(CustomOAuth2User customOAuth2User) {
    return new UsernamePasswordAuthenticationToken
        (customOAuth2User.getAuthId(), null, customOAuth2User.getAuthorities());
  }
}
