package com.health.service.mealservice.component;

import com.health.service.mealservice.client.FoodPublicDataClient;
import com.health.service.mealservice.dto.FoodPublicDataDto;
import com.health.service.mealservice.dto.FoodPublicDataMap;
import com.health.service.mealservice.dto.FoodPublicDataMap.Item;
import com.health.service.mealservice.dto.ItemDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class FoodPublicDataComponent {

  private final FoodPublicDataClient foodPublicDataClient;

  @Value("${public-data.key}")
  private String serviceKey;


  public List<FoodPublicDataDto> getApiDtoList(int pageNum) {

    FoodPublicDataMap jsonMap =
        foodPublicDataClient.getFoodList(serviceKey, pageNum, 100, "json").getBody();

//    log.info("{}", pageNum);

    return getFoodDto(jsonMap);
  }

  private List<FoodPublicDataDto> getFoodDto(FoodPublicDataMap map) {

    return map.getBody().getItems().stream()
        .map(this::mappingFoodPublicDataDto)
        .toList();
  }

  private FoodPublicDataDto mappingFoodPublicDataDto(Item item) {

    return FoodPublicDataDto.fromItemDto(convertValueFromItem(item));
  }

  private Integer getNutrientValue(String str) {

    return Integer.parseInt(str.isEmpty() || str.isBlank() ? "0" : str);
  }

  private Integer getAmountValue(String str) {
    // 값에서 단위(g, ml...)를 제거한 숫자만 추출
    if (str == null) {
      str = "";
    } else {
      str = str.replaceAll("[^0-9]", "");
    }

    // 총 중량 데이터 없는 식품들은 100g당 열량 데이터만 들어 있음으로 중량을 100으로 설정
    return Integer.parseInt(str.isEmpty() || str.isBlank() ? "100" : str);
  }

  private ItemDto convertValueFromItem(Item item) {
    String code = item.getFoodCode();
    String name = item.getName();
    log.info("{}", name);

    Integer weight = getAmountValue(item.getWeight());
    Integer kcal = getNutrientValue(item.getKCal());
    Integer protein = getNutrientValue(item.getProtein());
    Integer fat = getNutrientValue(item.getFat());
    Integer carbohydrate = getNutrientValue(item.getCarbohydrate());

    // 총 중량이 100이 아닌 경우 총 중량에 맞춰서 영양성분 업데이트
    if (weight != 100) {
      Double ratio = weight / 100.0;
      kcal = (int) (ratio * kcal);
      protein = (int) (ratio * protein);
      fat = (int) (ratio * fat);
      carbohydrate = (int) (ratio * carbohydrate);
    }

    return ItemDto.builder()
        .foodCode(code)
        .name(name)
        .weight(weight)
        .kCal(kcal)
        .protein(protein)
        .fat(fat)
        .carbohydrate(carbohydrate)
        .build();
  }


}
