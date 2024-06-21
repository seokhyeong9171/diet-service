package com.health.security.config;

import static org.springframework.security.config.http.SessionCreationPolicy.*;

import com.health.security.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final CustomOAuth2UserService oAuth2UserService;
  private final ClientRegistrationRepository clientRegistrationRepository;
  private final OAuth2AuthorizedClientService oAuth2AuthorizedClientService;


  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    http
        .csrf(AbstractHttpConfigurer::disable)
        .formLogin(AbstractHttpConfigurer::disable)
        .httpBasic(AbstractHttpConfigurer::disable)
        .sessionManagement(session ->session.sessionCreationPolicy(STATELESS));

    http
        .oauth2Login(oauth2 -> oauth2
            .clientRegistrationRepository(clientRegistrationRepository)
            .authorizedClientService(oAuth2AuthorizedClientService)
            .userInfoEndpoint(userInfoEndpointConfig ->
                userInfoEndpointConfig.userService(oAuth2UserService))
        );

    http
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/", "/login/**", "/oauth2/**").permitAll()
            .anyRequest().authenticated()
        );

    return http.build();
  }

}
