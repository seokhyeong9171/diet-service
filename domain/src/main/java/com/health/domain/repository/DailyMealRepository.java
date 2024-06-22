package com.health.domain.repository;

import com.health.domain.entity.DailyMealEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyMealRepository extends JpaRepository<DailyMealEntity, Long> {

}
