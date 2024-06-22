package com.health.api.form;

import com.health.domain.form.UserNicknameDomainForm;
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
public class UserNicknameForm {

  @Size(max = 10)
  @NotBlank
  private String nickname;

  public UserNicknameDomainForm toDomainForm() {
    return UserNicknameDomainForm.builder().nickname(nickname).build();
  }

}
