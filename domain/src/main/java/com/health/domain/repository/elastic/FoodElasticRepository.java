package com.health.domain.repository.elastic;

import com.health.domain.entity.FoodElasticEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodElasticRepository extends ElasticsearchRepository<FoodElasticEntity, String> {

  Page<FoodElasticEntity> findByFoodName(String foodName, Pageable pageable);

  Page<FoodElasticEntity> findByFoodNameContaining(String foodName, Pageable pageable);

}
