package com.health.forumservice.service.impl;

import static com.health.domain.exception.ErrorCode.*;
import static com.health.redisservice.component.RedisKeyComponent.*;

import com.health.domain.exception.CustomException;
import com.health.forumservice.dto.PostDomainDto;
import com.health.domain.entity.PostEntity;
import com.health.domain.entity.PostLikeEntity;
import com.health.domain.entity.PostViewEntity;
import com.health.domain.entity.UserEntity;
import com.health.forumservice.form.PostServiceForm;
import com.health.domain.repository.CommentRepository;
import com.health.domain.repository.PostLikeRepository;
import com.health.domain.repository.PostRepository;
import com.health.domain.repository.PostViewRepository;
import com.health.domain.repository.UserRepository;
import com.health.forumservice.service.PostService;
import com.health.redisservice.component.RedisKeyComponent;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

  private final UserRepository userRepository;
  private final PostRepository postRepository;
  private final CommentRepository commentRepository;
  private final PostViewRepository postViewRepository;
  private final PostLikeRepository postLikeRepository;

  private final RedisTemplate<String, String> redisTemplate;

  @Override
  @Transactional(readOnly = true)
  public Page<PostDomainDto> getPostList(Pageable pageable) {

    return postRepository.findAll(pageable).map(PostDomainDto::fromEntity);
  }

  @Override
  public PostDomainDto createPost(String authId, PostServiceForm serviceForm) {

    UserEntity findUser = findUserByAuthId(authId);

    PostEntity createdPost = PostEntity.createFromForm(findUser, serviceForm.toDomainForm());
    PostEntity savedPost = postRepository.save(createdPost);

    findUser.getPostList().add(savedPost);

    // Redis zSet에 넣어줌
    ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();
    zSetOps.add(postLikeCountKey(), savedPost.getId().toString(), 0);
    zSetOps.add(postViewCountKey(), savedPost.getId().toString(), 0);

    return PostDomainDto.fromEntity(savedPost);
  }

  @Override
  public PostDomainDto updatePost(String authId, Long postId, PostServiceForm serviceForm) {

    UserEntity findUser = findUserByAuthId(authId);
    PostEntity findPost = findPostById(postId);

    validateCreatedUser(findUser, findPost);

    findPost.updateFromForm(serviceForm.toDomainForm());

    return PostDomainDto.fromEntity(findPost);
  }

  @Override
  public Long deletePost(String authId, Long postId) {

    ZSetOperations<String, String> zSetOps = getZSetOps();
    SetOperations<String, String> setOps = getSetOps();

    UserEntity findUser = findUserByAuthId(authId);
    PostEntity findPost = findPostById(postId);

    validateCreatedUser(findUser, findPost);

    findPost.getCommentList().forEach(commentRepository::deleteByParentComment);
    commentRepository.deleteByPost(findPost);
    postRepository.delete(findPost);

    zSetOps.remove(postLikeCountKey(), postId.toString());
    zSetOps.remove(postViewCountKey(), postId.toString());

    setOps.remove(userPostLikeKey(authId), postId);
    setOps.remove(userPostViewKey(authId), postId);

    return findPost.getId();
  }

  @Override
  @Transactional(readOnly = true)
  public int getPostLikeCount(Long postId) {

    Double likeCount = redisTemplate.opsForZSet().score(postLikeCountKey(), postId.toString());

    validateRedisNotNull(likeCount);

    return likeCount.intValue();

  }

  @Override
  @Transactional(readOnly = true)
  public int getPostViewCount(Long postId) {

    Double viewCount = redisTemplate.opsForZSet().score(postViewCountKey(), postId.toString());

    validateRedisNotNull(viewCount);

    return viewCount.intValue();

  }

  @Override
  public PostDomainDto getPostInfo(String authId, Long postId) {
    UserEntity findUser = findUserByAuthId(authId);
    PostEntity findPost = findPostById(postId);

    SetOperations<String, String> setOps = getSetOps();

    // 처음 조회한 유저일 경우 유저 조회 게시글 추가 & 게시글 조회수 증가
    if (!isContainsRedisSetValue(userPostViewKey(authId), postId.toString())) {

      postViewRepository.save(PostViewEntity.createNew(findUser, findPost));

      setOps.add(userPostViewKey(authId), postId.toString());
      getZSetOps().incrementScore(postViewCountKey(), postId.toString(), 1);
    }

    return PostDomainDto.fromEntity(findPost);
  }

  @Override
  public Integer postAddLike(String authId, Long postId) {

    SetOperations<String, String> setOps = getSetOps();
    ZSetOperations<String, String> zSetOps = getZSetOps();

    // 이미 해당 유저가 해당 게시글에 좋아요를 누른 상태인지 확인
    validateAlreadyLike(authId, postId);

    UserEntity findUser = findUserByAuthId(authId);
    PostEntity findPost = findPostById(postId);

    postLikeRepository.save(PostLikeEntity.createNew(findUser, findPost));

    setOps.add(userPostLikeKey(authId), postId.toString());
    zSetOps.incrementScore(postLikeCountKey(), postId.toString(), 1);

    return Objects.requireNonNull(zSetOps.score(postLikeCountKey(), postId)).intValue();
  }

  @Override
  public Integer postUnLike(String authId, Long postId) {

    SetOperations<String, String> setOps = getSetOps();
    ZSetOperations<String, String> zSetOps = getZSetOps();

    // 해당 유저가 해당 게시글에 좋아요 한 상태인지 확인
    validateNotLike(authId, postId);

    setOps.remove(userPostLikeKey(authId), postId);
    zSetOps.incrementScore(postLikeCountKey(), postId.toString(), -1);

    return Objects.requireNonNull(zSetOps.score(postLikeCountKey(), postId)).intValue();
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

  private SetOperations<String, String> getSetOps() {
    return redisTemplate.opsForSet();
  }

  private ZSetOperations<String, String> getZSetOps() {
    return redisTemplate.opsForZSet();
  }

  private void validateRedisNotNull(Double value) {
    if (value == null) {
      throw new CustomException(REDIS_OBJECT_NOT_EXIST);
    }
  }

  private boolean isContainsRedisSetValue(String key, String value) {
    return Boolean.TRUE.equals(getSetOps().isMember(key, value));
  }

  private void validateAlreadyLike(String authId, Long postId) {
    if (isContainsRedisSetValue(userPostLikeKey(authId), postId.toString())) {
      throw new CustomException(POST_LIKE_ALREADY_EXIST);
    }
  }

  private void validateNotLike(String authId, Long postId) {
    if (isContainsRedisSetValue(userPostLikeKey(authId), postId.toString())) {
      throw new CustomException(POST_LIKE_NOT_EXIST);
    }
  }

}
