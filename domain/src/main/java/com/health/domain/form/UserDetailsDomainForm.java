package com.health.domain.form;

import com.health.domain.type.Region;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetailsDomainForm {

  private Double height;

  private Double weight;

  private Double goalWeight;

  private Region region;

}
