package com.health.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.oauth2.client.JdbcOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

@Configuration
@RequiredArgsConstructor
public class OAuth2Config {

  private final JdbcTemplate jdbcTemplate;


  @Bean
  public ClientRegistration naverClientRegistration() {

    return ClientRegistration.withRegistrationId("naver")
        .clientId("3H9meS3lgaoePfK4ps6s")
        .clientSecret("WxsOAYHjuE")
        .redirectUri("http://localhost:8080/login/oauth2/code/naver")
        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
        .scope("name", "gender", "birthyear", "birthday")
        .authorizationUri("https://nid.naver.com/oauth2.0/authorize")
        .tokenUri("https://nid.naver.com/oauth2.0/token")
        .userInfoUri("https://openapi.naver.com/v1/nid/me")
        .userNameAttributeName("response")
        .build();
  }

  @Bean
  public OAuth2AuthorizedClientService oAuth2AuthorizedClientService() {

    return new JdbcOAuth2AuthorizedClientService
        (jdbcTemplate ,clientRegistrationRepository());
  }

  @Bean
  public ClientRegistrationRepository clientRegistrationRepository() {
    return new InMemoryClientRegistrationRepository(naverClientRegistration());
  }

}
