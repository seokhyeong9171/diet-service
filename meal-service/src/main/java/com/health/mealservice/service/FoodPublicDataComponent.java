package com.health.mealservice.service;

import static com.health.common.exception.ErrorCode.*;
import static com.health.mealservice.type.FoodPublicDataKey.*;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.health.common.exception.CustomException;
import com.health.mealservice.client.FoodPublicDataClient;
import com.health.mealservice.dto.FoodPublicDataDto;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
@Slf4j
public class FoodPublicDataComponent {

  private final FoodPublicDataClient foodPublicDataClient;

  @Value("${public-data.key}")
  private String serviceKey;


  public List<FoodPublicDataDto> getApiDtoList(int pageNum) {

    String json =
        foodPublicDataClient.getFoodList(serviceKey, pageNum, 100, "json").getBody();

//    log.info("{}", pageNum);

    return getFoodDto(json);
  }

  public Integer getTotalNum() {

    String json =
        foodPublicDataClient.getFoodList(serviceKey, 1, 1, "json").getBody();

    if (!StringUtils.hasText(json)) {
      throw new CustomException(API_NOT_WORKING);
    }

    JsonObject asJsonObject;
    try {
      asJsonObject = JsonParser.parseString(json).getAsJsonObject().get("body").getAsJsonObject();

    } catch (Exception e) {
      log.error(e.getMessage());
      return 0;
    }

    return asJsonObject.get("totalCount").getAsInt();
  }

  private List<FoodPublicDataDto> getFoodDto(String json) {

    List<FoodPublicDataDto> list = new ArrayList<>();

    JsonArray jsonFoodList;

    try {
      jsonFoodList = JsonParser.parseString(json)
          .getAsJsonObject().get("body")
          .getAsJsonObject().get("items")
          .getAsJsonArray();

    } catch (Exception e) {
      log.error(e.getMessage());
      return list;
    }

    for (JsonElement item : jsonFoodList) {
      try {

        JsonObject itemObject = item.getAsJsonObject();

        FoodPublicDataDto foodPublicDataDto = mappingFoodPublicDataDto(itemObject);

        list.add(foodPublicDataDto);

      } catch (Exception e) {
        log.error(e.getMessage());
        continue;
      }
    }

    return list;
  }

  private FoodPublicDataDto mappingFoodPublicDataDto(JsonObject itemObject) {

    String code = itemObject.get(CODE.key()).getAsString();
    String name = itemObject.get(NAME.key()).getAsString();
//        log.info("{}", name);

    Integer weight = getAmountValue(itemObject.get(WEIGHT.key()));
    Integer kcal = getNutrientValue(itemObject.get(KCAL.key()));
    Integer protein = getNutrientValue(itemObject.get(PROTEIN.key()));
    Integer fat = getNutrientValue(itemObject.get(FAT.key()));
    Integer carbohydrate = getNutrientValue(itemObject.get(CARBOHYDRATE.key()));

    return FoodPublicDataDto.builder()
        .foodCode(code)
        .foodName(name)
        .foodAmount(weight)
        .kCal(kcal)
        .protein(protein)
        .fat(fat)
        .carbohydrate(carbohydrate)
        .build();
  }

  private Integer getNutrientValue(JsonElement jsonElement) {
    String str = getStringValue(jsonElement);

    return Integer.parseInt(str.isEmpty() || str.isBlank() ? "0" : str);
  }

  private Integer getAmountValue(JsonElement jsonElement) {
    // 값에서 단위(g, ml...)를 제거한 숫자만 추출
    String str = getStringValue(jsonElement).replaceAll("[^0-9]", "");

    // 총 중량 데이터 없는 식품들은 100g당 열량 데이터 들어 있음으로 중량을 100으로 설정
    return Integer.parseInt(str.isEmpty() || str.isBlank() ? "100" : str);
  }

  private String getStringValue(JsonElement jsonElement) {
    return jsonElement.isJsonNull() ? "" : jsonElement.getAsString();
  }


}
