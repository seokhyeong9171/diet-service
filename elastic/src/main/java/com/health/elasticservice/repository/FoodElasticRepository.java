package com.health.elasticservice.repository;

import com.health.elasticservice.dto.FoodElasticDto;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodElasticRepository extends ElasticsearchRepository<FoodElasticDto, String>{

  Page<FoodElasticDto> findByFoodName(String foodName, Pageable pageable);

  Page<FoodElasticDto> findByFoodNameContaining(String foodName, Pageable pageable);

}
