package com.health.forumservice.service.impl;

import static com.health.common.exception.ErrorCode.*;
import static com.health.common.redis.RedisKeyComponent.*;

import com.health.common.exception.CustomException;
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

    return postRepository.findAll(pageable).map(PostDomainDto::fromEntity);
  }

  @Override
  public PostDomainDto createPost(String authId, PostDomainForm form) {

    UserEntity findUser = findUserByAuthId(authId);

    PostEntity createdPost = PostEntity.createFromForm(findUser, form);
    PostEntity savedPost = postRepository.save(createdPost);

    // Redis zSet에 넣어줌
    ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();
    zSetOps.add(postLikeValueKey(), savedPost.getId().toString(), 0);

    return PostDomainDto.fromEntity(savedPost);
  }

  @Override
  public PostDomainDto updatePost(String authId, Long postId, PostDomainForm domainForm) {

    UserEntity findUser = findUserByAuthId(authId);
    PostEntity findPost = findPostById(postId);

    validateCreatedUser(findUser, findPost);

    findPost.updateFromForm(domainForm);

    return PostDomainDto.fromEntity(findPost);
  }

  @Override
  public Long deletePost(String authId, Long postId) {

    UserEntity findUser = findUserByAuthId(authId);
    PostEntity findPost = findPostById(postId);

    validateCreatedUser(findUser, findPost);

    postRepository.delete(findPost);
    redisTemplate.opsForZSet().remove(postLikeValueKey(), postId.toString());

    // TODO
    //  해당 게시물에 좋아요 누른 유저 cache 제거

    return findPost.getId();
  }

  @Override
  public Integer getPostLikeValue(Long postId) {

    ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();
    Double score = zSetOps.score(postLikeValueKey(), postId.toString());

    if (score != null) {
      return score.intValue();

    } else {
      // Redis에 해당 값이 없을 경우 DB에서 조회해서 가져옴
      PostEntity postById = findPostById(postId);
      int likeInDb = postById.getLike();
      zSetOps.add(postLikeValueKey(), postId.toString(), likeInDb);
      return likeInDb;
    }
  }

  private UserEntity findUserByAuthId(String authId) {
    return userRepository.findByAuthId(authId)
        .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
  }

  private PostEntity findPostById(Long postId) {
    return postRepository.findById(postId)
        .orElseThrow(() -> new CustomException(POST_NOT_FOUND));
  }

  private void validateCreatedUser(UserEntity findUser, PostEntity findPost) {
    if (findUser != findPost.getCreateUser()) {
      throw new CustomException(POST_NOT_CREATE_USER);
    }
  }

}
