package com.health.repository;

import com.health.entity.CommentEntity;
import com.health.entity.DailyMealEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyMealRepository extends JpaRepository<DailyMealEntity, Long> {

}
