package com.health.security.jwt;

import static com.health.security.constant.SecurityConstant.*;

import com.health.security.dto.CustomOAuth2User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

  private final JwtUtil jwtUtil;


  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String authorization = null;
    Cookie[] cookies = request.getCookies();
    for (Cookie cookie : cookies) {
      if (cookie.getName().equals(AUTHORIZATION_COOKIE_NAME)) {
        authorization = cookie.getValue();
      }
    }

    if (authorization == null) {
      filterChain.doFilter(request, response);

      return;
    }

    String jwtToken = authorization;

    if (jwtUtil.isExpired(jwtToken)) {
      filterChain.doFilter(request, response);
      return;
    }

    String authId = jwtUtil.getAuthId(jwtToken);
    String role = jwtUtil.getRole(jwtToken);

    CustomOAuth2User customOAuth2User = CustomOAuth2User.forAuthentication(authId, role);

    Authentication authentication = getAuthentication(customOAuth2User);

    SecurityContextHolder.getContext().setAuthentication(authentication);

    filterChain.doFilter(request, response);
  }



  private UsernamePasswordAuthenticationToken getAuthentication(CustomOAuth2User customOAuth2User) {
    return new UsernamePasswordAuthenticationToken
        (customOAuth2User.getAuthId(), null, customOAuth2User.getAuthorities());
  }
}
