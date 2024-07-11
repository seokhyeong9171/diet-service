package com.health.mealservice.service.impl;

import com.health.elasticservice.dto.FoodElasticDto;
import com.health.domain.entity.FoodEntity;
import com.health.domain.repository.FoodRepository;
import com.health.elasticservice.repository.FoodElasticRepository;
import com.health.mealservice.component.FoodPublicDataComponent;
import com.health.mealservice.dto.FoodPublicDataDto;
import com.health.mealservice.service.FoodDataService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class FoodPublicDataServiceImpl implements FoodDataService {

  private final FoodPublicDataComponent foodPublicDataComponent;
  private final FoodElasticRepository foodElasticRepository;
  private final FoodRepository foodRepository;

  public void getAndSaveFoodData() {

    // 공공데이터에서 한번에 식품 정보 최대 100개 까지 가져올 수 있음
    int index = 1;

    while (true) {

      List<FoodPublicDataDto> foodInfoList = foodPublicDataComponent.getApiDtoList(index++);

      // 리스트가 빈 상태이면 모든 데이터 조회 완료 상태
      if (foodInfoList.isEmpty()) break;

      List<FoodEntity> FoodEntityList =
          foodInfoList.stream().map(FoodPublicDataDto::toEntity).toList();

      foodElasticRepository.saveAll(
          FoodEntityList.stream().map(FoodElasticDto::fromEntity).toList()
      );

      foodRepository.saveAll(FoodEntityList);
    }
  }

}
