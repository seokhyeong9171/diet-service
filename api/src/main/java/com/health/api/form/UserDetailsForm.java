package com.health.api.form;

import com.health.domain.type.Region;
import com.health.service.userservice.form.UserDetailsServiceForm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetailsForm {


  private Double height;

  private Double weight;

  private Double goalWeight;

  private Region region;

  public UserDetailsServiceForm toDomainForm() {
    return UserDetailsServiceForm.builder()
        .height(height)
        .weight(weight)
        .goalWeight(goalWeight)
        .region(region)
        .build();
  }

}
