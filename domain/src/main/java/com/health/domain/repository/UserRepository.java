package com.health.domain.repository;

import com.health.domain.entity.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

  boolean existsByAuthId(String authId);

  boolean existsByNickname(String nickname);

  Optional<UserEntity> findByAuthId(String authId);

}
