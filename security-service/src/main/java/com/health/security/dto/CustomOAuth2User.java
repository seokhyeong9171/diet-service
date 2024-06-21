package com.health.security.dto;

import com.health.type.RoleType;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class CustomOAuth2User implements OAuth2User {

  private final UserSecurityDto userSecurityDto;

  public CustomOAuth2User(UserSecurityDto userSecurityDto) {
    this.userSecurityDto = userSecurityDto;
  }

  public static CustomOAuth2User forAuthentication(String authId, String role) {
    return new CustomOAuth2User(authId, role);
  }

  private CustomOAuth2User(String authId, String role) {
    this.userSecurityDto = UserSecurityDto.builder()
        .authId(authId)
        .role(RoleType.valueOf(role))
        .build();
  }

  @Override
  public Map<String, Object> getAttributes() {
    return null;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(userSecurityDto.getRole().name()));
  }

  @Override
  public String getName() {
    return userSecurityDto.getAuthId();
  }

  public String getAuthId() {
    return getName();
  }
}
