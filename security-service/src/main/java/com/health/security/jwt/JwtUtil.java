package com.health.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtUtil {

  private final Environment env;
  private final SecretKey secretKey;


  public String createToken(String authId, String role) {

    Long expiredMs = Long.valueOf(env.getProperty("jwt.expire-ms"));

    return Jwts.builder()
        .signWith(secretKey)
        .claim("authId", authId)
        .claim("role", role)
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + expiredMs))
        .compact();
  }

  public String getAuthId(String jwt) {
    return getClaims(jwt).get("authId", String.class);
  }

  public String getRole(String jwt) {
    return getClaims(jwt).get("role", String.class);
  }

  public boolean isExpired(String jwt) {
    Date expiration = getClaims(jwt).getExpiration();
    return expiration.before(new Date(System.currentTimeMillis()));
  }


  private Claims getClaims(String jwt) {
    return Jwts.parser()
        .verifyWith(secretKey).build()
        .parseSignedClaims(jwt)
        .getPayload();
  }

}
