package com.health.mealservice.type;

public enum FoodPublicDataKey {

  CODE("FOOD_CD"),
  NAME("FOOD_NM_KR "),
  WEIGHT("Z10500"),
  KCAL("AMT_NUM1 "),
  PROTEIN("AMT_NUM3 "),
  FAT("AMT_NUM4"),
  CARBOHYDRATE("AMT_NUM6");


  private final String value;

  FoodPublicDataKey(String value) {
    this.value = value;
  }

  public String key() {
    return this.value;
  }

}
