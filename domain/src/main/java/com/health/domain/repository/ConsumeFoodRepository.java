package com.health.domain.repository;

import com.health.domain.entity.ConsumeFoodEntity;
import com.health.domain.entity.MealEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsumeFoodRepository extends JpaRepository<ConsumeFoodEntity, Long> {

  @Modifying
  @Query("delete from consume_food cf where cf.meal in :meals")
  void deleteByMeals(@Param("meals") List<MealEntity> meals);

}
