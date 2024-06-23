package com.health.domain.repository.query;

import com.health.domain.entity.QUserEntity;
import com.health.domain.entity.UserEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserQueryRepository {

  private final JPAQueryFactory jpaQueryFactory;

  public UserEntity findByAuthId(String authId) {
    QUserEntity userEntity = QUserEntity.userEntity;

    return jpaQueryFactory.selectFrom(userEntity)
        .where(userEntity.authId.eq(authId))
        .fetchOne();
  }

}
