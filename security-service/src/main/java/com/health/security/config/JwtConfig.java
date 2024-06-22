package com.health.security.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import java.nio.charset.StandardCharsets;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.oauth2.jwt.Jwt;

@Configuration
@RequiredArgsConstructor
public class JwtConfig {

  private final Environment env;

  @Bean
  public SecretKey secretKey() {
    return new SecretKeySpec(
        env.getProperty("jwt.secret").getBytes(StandardCharsets.UTF_8),
        Jwts.SIG.HS256.key().build().getAlgorithm()
    );
  }

}
