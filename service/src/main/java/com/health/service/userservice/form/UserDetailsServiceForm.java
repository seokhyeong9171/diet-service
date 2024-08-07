package com.health.service.userservice.form;

import com.health.domain.form.UserDetailsDomainForm;
import com.health.domain.type.Region;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetailsServiceForm {

  private Double height;

  private Double weight;

  private Double goalWeight;

  private Region region;

  public UserDetailsDomainForm toDomainForm() {
    return UserDetailsDomainForm.builder()
        .height(height).weight(weight).goalWeight(goalWeight).region(region)
        .build();
  }

}
