package com.health.security.dto;

import com.health.dto.UserDomainDto;
import com.health.security.response.OAuth2Response;
import com.health.type.Gender;
import com.health.type.RoleType;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSecurityDto {

  private String username;

  private String authId;

  private LocalDate birth;

  private Gender gender;

  private RoleType role;

  public static UserSecurityDto fromResponse(OAuth2Response oAuth2Response, RoleType role) {
    return UserSecurityDto.builder()
        .username(oAuth2Response.getName())
        .authId(oAuth2Response.getAuthId())
        .birth(oAuth2Response.getBirth())
        .gender(oAuth2Response.getGender())
        .role(role)
        .build();
  }

  public UserDomainDto toDomainDto() {
    return UserDomainDto.builder()
        .username(this.username)
        .authId(this.authId)
        .birth(this.birth)
        .gender(this.gender)
        .role(this.role)
        .build();
  }

}
