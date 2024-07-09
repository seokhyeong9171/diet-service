package com.health.domain.repository;

import com.health.domain.entity.PostEntity;
import com.health.domain.entity.PostViewEntity;
import com.health.domain.entity.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostViewRepository extends JpaRepository<PostViewEntity, Long> {

  boolean existsByViewedUserAndPost(UserEntity viewedUser, PostEntity post);

}
