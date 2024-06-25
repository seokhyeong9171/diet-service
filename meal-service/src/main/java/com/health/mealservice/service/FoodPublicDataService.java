package com.health.mealservice.service;

import com.health.domain.entity.FoodEntity;
import com.health.domain.repository.FoodRepository;
import com.health.mealservice.dto.FoodPublicDataDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class FoodPublicDataService {

  private final FoodPublicDataComponent foodPublicDataComponent;
  private final FoodRepository foodRepository;

  public void getFoodData() {

    int totalNum = foodPublicDataComponent.getTotalNum();
    int lastNum = totalNum / 100 + 1;

    for (int i = 1; i < lastNum + 1; i++) {

      List<FoodPublicDataDto> foodInfoList = foodPublicDataComponent.getApiDtoList(i);

      List<FoodEntity> entityList = foodInfoList.stream().map(FoodPublicDataDto::toEntity).toList();

      foodRepository.saveAll(entityList);
    }

  }

}
