package com.health.security.service;

import static com.health.domain.type.RoleType.*;

import com.health.domain.dto.UserDomainDto;
import com.health.domain.entity.UserEntity;
import com.health.domain.repository.UserRepository;
import com.health.security.dto.CustomOAuth2User;
import com.health.security.dto.UserSecurityDto;
import com.health.security.response.OAuth2Response;
import com.health.security.response.impl.NaverOAuth2Response;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

  private final UserRepository userRepository;


  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    OAuth2User oAuth2User = super.loadUser(userRequest);

    String registrationId = userRequest.getClientRegistration().getRegistrationId();

    OAuth2Response oAuth2Response;

    if (registrationId.equals("naver")) {
      oAuth2Response = new NaverOAuth2Response(oAuth2User.getAttributes());

    } else {
      return null;
    }

    UserSecurityDto userSecurityDto = UserSecurityDto.fromResponse(oAuth2Response, ROLE_USER);

    String authId = oAuth2Response.getAuthId();
    boolean existsByAuthId = userRepository.existsByAuthId(authId);
    if (!existsByAuthId) {
      UserDomainDto userDomainDto = userSecurityDto.toDomainDto();
      UserEntity registeredUser = UserEntity.register(userDomainDto);
      userRepository.save(registeredUser);
    }

    return new CustomOAuth2User(userSecurityDto);
  }
}
