package com.health.userservice.service.impl;

import static com.health.common.exception.ErrorCode.USER_NOT_FOUND;

import com.health.common.exception.CustomException;
import com.health.domain.dto.UserDomainDto;
import com.health.domain.entity.UserEntity;
import com.health.domain.form.UserInfoDomainForm;
import com.health.domain.repository.UserRepository;
import com.health.userservice.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserInfoServiceImpl implements UserInfoService {

  private final UserRepository userRepository;


  @Override
  @Transactional(readOnly = true)
  public UserDomainDto getUserInfo(String authId) {

    UserEntity findUser = getFindUser(authId);


    return UserDomainDto.fromEntity(findUser);
  }

  @Override
  public UserDomainDto updateUserInfo(String authId, UserInfoDomainForm form) {
    UserEntity findUser = getFindUser(authId);

    findUser.updateInfo(form);

    return UserDomainDto.fromEntity(findUser);
  }




  private UserEntity getFindUser(String authId) {
    return userRepository.findByAuthId(authId)
        .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
  }
}
