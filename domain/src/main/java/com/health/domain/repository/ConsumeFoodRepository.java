package com.health.domain.repository;

import com.health.domain.entity.ConsumeFoodEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsumeFoodRepository extends JpaRepository<ConsumeFoodEntity, Long> {

}
