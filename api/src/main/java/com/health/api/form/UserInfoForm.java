package com.health.api.form;

import com.health.domain.form.UserInfoDomainForm;
import com.health.domain.type.Region;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfoForm {

  @NotBlank
  @Size(max = 10)
  private String nickname;

  private Double height;

  private Double weight;

  private Double goalWeight;

  private Region region;

  public UserInfoDomainForm toDomainForm() {
    return UserInfoDomainForm.builder()
        .nickname(nickname)
        .height(height)
        .weight(weight)
        .goalWeight(goalWeight)
        .region(region)
        .build();
  }

}
