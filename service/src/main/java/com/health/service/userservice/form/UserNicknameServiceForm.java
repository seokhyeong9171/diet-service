package com.health.service.userservice.form;

import com.health.domain.form.UserNicknameDomainForm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserNicknameServiceForm {

  private String nickname;

  public UserNicknameDomainForm toDomainForm() {
    return UserNicknameDomainForm.builder().nickname(nickname).build();
  }

}
