package com.health.domain.repository;

import com.health.domain.entity.DailyMealEntity;
import com.health.domain.entity.MealEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MealRepository extends JpaRepository<MealEntity, Long> {

  @EntityGraph(attributePaths = {"dailyMeal", "dailyMeal.user"})
  Optional<MealEntity> findById(Long id);

  @Modifying
  @Query("delete from meal m where m.dailyMeal in :dailyMeal")
  void deleteByDailyMeal(@Param("dailyMeal")DailyMealEntity dailyMeal);

}
