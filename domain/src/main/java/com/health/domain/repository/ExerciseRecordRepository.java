package com.health.domain.repository;

import com.health.domain.entity.ExerciseRecordEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseRecordRepository extends JpaRepository<ExerciseRecordEntity, Long> {

  Page<ExerciseRecordEntity> findAllByUserAuthIdOrderById(String authId, Pageable pageable);


}
