package com.health.service.userservice.util;

import com.health.domain.entity.UserEntity;
import java.time.LocalDate;


public class UserCalorieUtil {

  public static Integer calculateAbleCalorie(UserEntity user) {

    int variableValue = 0;

    switch (user.getGender()) {
      case MALE -> variableValue = 5;
      case FEMALE -> variableValue = -161;
      case UNDEFINED -> variableValue = -78;
    }

    double bmr = calculateBmr(user, variableValue);

    // tdee - 500;
    return (int) (bmr * 1.5) - 500;
  }

  private static double calculateBmr(UserEntity user, int variableValue) {
    return 10 * user.getWeight() + 6.25 * user.getHeight() - 5 * getAge(user.getBirth())
        + variableValue;
  }

  private static int getAge(LocalDate birth) {
    LocalDate now = LocalDate.now();

    int currentYear = now.getYear();
    int currentMonth = now.getMonthValue();
    int currentDay = now.getDayOfMonth();

    int age = currentYear - birth.getYear();

    if (birth.getMonthValue() * 100 + birth.getDayOfMonth() > currentMonth * 100 + currentDay) {
      age--;
    }

    return age;
  }

}
