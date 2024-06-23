package com.health.domain.repository;

import com.health.domain.entity.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

  boolean existsByAuthId(String authId);

  boolean existsByNickname(String nickname);

  @Query("select u from com.health.domain.entity.UserEntity u where u.authId = :authId")
  Optional<UserEntity> findByAuthId(String authId);

}
