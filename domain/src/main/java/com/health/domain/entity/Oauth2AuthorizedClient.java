package com.health.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "oauth2_authorized_client")
public class Oauth2AuthorizedClient {

  @EmbeddedId
  private Oauth2AuthorizedClientId id;

  @Column(name = "access_token_type", nullable = false, length = 100)
  private String accessTokenType;

  @Column(name = "access_token_value", nullable = false)
  private byte[] accessTokenValue;

  @Column(name = "access_token_issued_at", nullable = false)
  private Instant accessTokenIssuedAt;

  @Column(name = "access_token_expires_at", nullable = false)
  private Instant accessTokenExpiresAt;

  @Column(name = "access_token_scopes", length = 1000)
  private String accessTokenScopes;

  @Column(name = "refresh_token_value")
  private byte[] refreshTokenValue;

  @Column(name = "refresh_token_issued_at")
  private Instant refreshTokenIssuedAt;

  @ColumnDefault("current_timestamp()")
  @Column(name = "created_at", nullable = false)
  private Instant createdAt;

}