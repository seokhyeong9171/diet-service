package com.health.userservice.service.impl;

import static com.health.common.exception.ErrorCode.NICKNAME_DUPLICATED;
import static com.health.common.exception.ErrorCode.USER_NOT_FOUND;

import com.health.common.exception.CustomException;
import com.health.domain.dto.UserDomainDto;
import com.health.domain.entity.ExerciseRecordEntity;
import com.health.domain.entity.UserEntity;
import com.health.domain.form.UserDetailsDomainForm;
import com.health.domain.form.UserNicknameDomainForm;
import com.health.domain.repository.ExerciseRecordRepository;
import com.health.domain.repository.UserRepository;
import com.health.domain.repository.query.UserQueryRepository;
import com.health.userservice.service.UserInfoService;
import java.util.Optional;
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
  public UserDomainDto updateUserDetails(String authId, UserDetailsDomainForm form) {
    UserEntity findUser = getFindUser(authId);

    findUser.updateDetails(form);

    return UserDomainDto.fromEntity(findUser);
  }

  @Override
  public String updateUserNickname(String authId, UserNicknameDomainForm form) {

    // 변경하려는 닉네임이 중복된 닉네임인지 확인
    validUniqueNickname(form);

    UserEntity findUser = getFindUser(authId);
    findUser.updateNickname(form);

    return findUser.getNickname();
  }


  private UserEntity getFindUser(String authId) {
    return userRepository.findByAuthId(authId)
        .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
  }

  private void validUniqueNickname(UserNicknameDomainForm form) {
    if (userRepository.existsByNickname(form.getNickname())) {
      throw new CustomException(NICKNAME_DUPLICATED);
    }
  }
}
