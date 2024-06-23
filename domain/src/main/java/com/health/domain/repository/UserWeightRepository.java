package com.health.domain.repository;

import com.health.domain.entity.UserEntity;
import com.health.domain.entity.UserWeightEntity;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserWeightRepository extends JpaRepository<UserWeightEntity, Long> {

  @EntityGraph(attributePaths = {"user"})
  Optional<UserWeightEntity> findWithUserInfoById(Long id);

  Page<UserWeightEntity> findAllByUser(UserEntity user, Pageable pageable);

  boolean existsByUserAndWeightRegDt(UserEntity user, LocalDate date);

}
