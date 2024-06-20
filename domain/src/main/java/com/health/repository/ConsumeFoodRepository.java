package com.health.repository;

import com.health.entity.ConsumeFoodEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsumeFoodRepository extends JpaRepository<ConsumeFoodEntity, Long> {

}
