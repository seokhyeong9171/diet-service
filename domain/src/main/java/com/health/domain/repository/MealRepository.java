package com.health.domain.repository;

import com.health.domain.entity.MealEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MealRepository extends JpaRepository<MealEntity, Long> {

  @EntityGraph(attributePaths = {"dailyMeal", "dailyMeal.user"})
  Optional<MealEntity> findById(Long id);

}
