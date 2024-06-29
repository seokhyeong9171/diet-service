package com.health.domain.repository;

import com.health.domain.entity.DailyMealEntity;
import com.health.domain.entity.UserEntity;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyMealRepository extends JpaRepository<DailyMealEntity, Long> {

  boolean existsByUserAndDailyMealDt(UserEntity user, LocalDate date);

  Optional<DailyMealEntity> findByUserAndDailyMealDt(UserEntity user, LocalDate date);

  Page<DailyMealEntity> findByUserOrderByDailyMealDtDesc(UserEntity user, Pageable pageable);

}
