package com.health.domain.repository;

import com.health.domain.entity.Oauth2AuthorizedClient;
import com.health.domain.entity.Oauth2AuthorizedClientId;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Oauth2AuthorizedClientRepository
    extends JpaRepository<Oauth2AuthorizedClient, Oauth2AuthorizedClientId> {

}
