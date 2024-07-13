package com.health.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

@Getter
@Setter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Oauth2AuthorizedClientId implements java.io.Serializable {

  private static final long serialVersionUID = -2194764910603450861L;

  @Column(name = "client_registration_id", nullable = false, length = 100)
  private String clientRegistrationId;

  @Column(name = "principal_name", nullable = false, length = 200)
  private String principalName;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    Oauth2AuthorizedClientId entity = (Oauth2AuthorizedClientId) o;
    return Objects.equals(this.clientRegistrationId, entity.clientRegistrationId) &&
        Objects.equals(this.principalName, entity.principalName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(clientRegistrationId, principalName);
  }


  public static Oauth2AuthorizedClientId fromAuthId(String authId) {
    String clientRegistrationId =
        authId.substring(0, authId.indexOf(0, '_'));

    return new Oauth2AuthorizedClientId(clientRegistrationId, authId);
  }

}