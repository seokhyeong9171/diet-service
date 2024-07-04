package com.health.forumservice.service.impl;

import static com.health.common.exception.ErrorCode.*;
import static com.health.common.redis.RedisKeyComponent.*;

import com.health.common.exception.CustomException;
import com.health.common.exception.ErrorCode;
import com.health.common.redis.RedisKeyComponent;
import com.health.domain.dto.PostDomainDto;
import com.health.domain.entity.PostEntity;
import com.health.domain.entity.UserEntity;
import com.health.domain.form.PostDomainForm;
import com.health.domain.repository.PostRepository;
import com.health.domain.repository.UserRepository;
import com.health.forumservice.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

  private final UserRepository userRepository;
  private final PostRepository postRepository;

  private final RedisTemplate<String, String> redisTemplate;

  @Override
  @Transactional(readOnly = true)

  public Page<PostDomainDto> getPostList(Pageable pageable) {

    return postRepository.findAll(pageable).map(post -> PostDomainDto.fromEntity(post, null));
  }

  @Override
  public PostDomainDto createPost(String authId, PostDomainForm form) {

    UserEntity findUser = findUserByAuthId(authId);

    PostEntity createdPost = PostEntity.createFromForm(findUser, form);
    PostEntity savedPost = postRepository.save(createdPost);

    ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();
    zSetOps.add(postLikeValue(), savedPost.getId().toString(), 0);

    return PostDomainDto.fromEntity(savedPost, 0);
  }

  private UserEntity findUserByAuthId(String authId) {
    return userRepository.findByAuthId(authId)
        .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
  }

}
