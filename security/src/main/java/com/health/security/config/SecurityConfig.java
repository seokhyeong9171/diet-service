package com.health.security.config;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import com.health.security.jwt.JwtFilter;
import com.health.security.oauth2.CustomSuccessHandler;
import com.health.security.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final CustomOAuth2UserService oAuth2UserService;
  private final ClientRegistrationRepository clientRegistrationRepository;
  private final OAuth2AuthorizedClientService oAuth2AuthorizedClientService;
  private final CustomSuccessHandler customSuccessHandler;
  private final JwtFilter jwtFilter;


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
            .successHandler(customSuccessHandler)
        );

    http
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/", "/login/**", "/oauth2/**").permitAll()
            .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
            .permitAll()
            .anyRequest().authenticated()
        );

    http
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

}
