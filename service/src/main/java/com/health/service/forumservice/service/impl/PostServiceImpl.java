package com.health.service.forumservice.service.impl;

import static com.health.domain.exception.ErrorCode.POST_LIKE_ALREADY_EXIST;
import static com.health.domain.exception.ErrorCode.POST_LIKE_NOT_EXIST;
import static com.health.domain.exception.ErrorCode.POST_NOT_CREATE_USER;
import static com.health.domain.exception.ErrorCode.POST_NOT_FOUND;
import static com.health.domain.exception.ErrorCode.REDIS_OBJECT_NOT_EXIST;
import static com.health.domain.exception.ErrorCode.USER_NOT_FOUND;
import static com.health.redisservice.component.RedisKeyComponent.postLikeCountKey;
import static com.health.redisservice.component.RedisKeyComponent.postViewCountKey;
import static com.health.redisservice.component.RedisKeyComponent.userPostLikeKey;
import static com.health.redisservice.component.RedisKeyComponent.userPostViewKey;

import com.health.domain.entity.PostEntity;
import com.health.domain.entity.PostLikeEntity;
import com.health.domain.entity.PostViewEntity;
import com.health.domain.entity.UserEntity;
import com.health.domain.exception.CustomException;
import com.health.domain.repository.CommentRepository;
import com.health.domain.repository.PostLikeRepository;
import com.health.domain.repository.PostRepository;
import com.health.domain.repository.PostViewRepository;
import com.health.domain.repository.UserRepository;
import com.health.service.forumservice.dto.PostServiceDto;
import com.health.service.forumservice.form.PostServiceForm;
import com.health.service.forumservice.service.PostService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
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
  public Page<PostServiceDto> getPostList(Pageable pageable) {

    return postRepository.findAll(pageable).map(PostServiceDto::fromEntity);
  }

  @Override
  public PostServiceDto createPost(String authId, PostServiceForm serviceForm) {

    UserEntity findUser = findUserByAuthId(authId);

    PostEntity createdPost = PostEntity.createFromForm(findUser, serviceForm.toDomainForm());
    PostEntity savedPost = postRepository.save(createdPost);

    findUser.getPostList().add(savedPost);

    // Redis zSet에 넣어줌
    redisTemplate.executePipelined((RedisCallback<?>) connection -> {

      StringRedisConnection redisConnection = (StringRedisConnection) connection;
      addPostInitialVal(redisConnection, postLikeCountKey(), savedPost);
      addPostInitialVal(redisConnection, postViewCountKey(), savedPost);
      return null;
    });

    return PostServiceDto.fromEntity(savedPost);
  }

  @Override
  public PostServiceDto updatePost(String authId, Long postId, PostServiceForm serviceForm) {

    UserEntity findUser = findUserByAuthId(authId);
    PostEntity findPost = findPostById(postId);

    validateCreatedUser(findUser, findPost);

    findPost.updateFromForm(serviceForm.toDomainForm());

    return PostServiceDto.fromEntity(findPost);
  }

  @Override
  public Long deletePost(String authId, Long postId) {

    UserEntity findUser = findUserByAuthId(authId);
    PostEntity findPost = findPostById(postId);

    validateCreatedUser(findUser, findPost);

    findPost.getCommentList().forEach(commentRepository::deleteByParentComment);
    commentRepository.deleteByPost(findPost);
    postRepository.delete(findPost);

    // Redis에서 해당 값 제거
    redisTemplate.executePipelined((RedisCallback<?>) connection -> {

      StringRedisConnection redisConnection = (StringRedisConnection) connection;

      redisConnection.zRem(postLikeCountKey(), postId.toString());
      redisConnection.zRem(postViewCountKey(), postId.toString());

      redisConnection.sRem(userPostLikeKey(authId), postId.toString());
      redisConnection.sRem(userPostViewKey(authId), postId.toString());

      return null;
    });

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
  public PostServiceDto getPostInfo(String authId, Long postId) {
    UserEntity findUser = findUserByAuthId(authId);
    PostEntity findPost = findPostById(postId);

    // 처음 조회한 유저일 경우 유저 조회 게시글 추가 & 게시글 조회수 증가
    if (!isContainsRedisSetValue(userPostViewKey(authId), postId.toString())) {

      postViewRepository.save(PostViewEntity.createNew(findUser, findPost));

      redisTemplate.executePipelined((RedisCallback<?>) connection -> {

        StringRedisConnection redisConnection = (StringRedisConnection) connection;

        redisConnection.sAdd(userPostViewKey(authId), postId.toString());
        plusPostZSetVal(redisConnection, postId);

        return null;
      });
    }

    return PostServiceDto.fromEntity(findPost);
  }

  @Override
  public Integer postAddLike(String authId, Long postId) {

    // 이미 해당 유저가 해당 게시글에 좋아요를 누른 상태인지 확인
    validateAlreadyLike(authId, postId);

    UserEntity findUser = findUserByAuthId(authId);
    PostEntity findPost = findPostById(postId);

    postLikeRepository.save(PostLikeEntity.createNew(findUser, findPost));

    List<Object> result = redisTemplate.executePipelined((RedisCallback<?>) connection -> {

      StringRedisConnection redisConnection = (StringRedisConnection) connection;
      redisConnection.sAdd(userPostLikeKey(authId), postId.toString());
      plusPostZSetVal(redisConnection, postId);
      // 반환할 좋아요 갯수 조회
      redisConnection.zScore(postLikeCountKey(), postId.toString());

      return null;
    });

    return Integer.valueOf((String) result.getLast());
  }

  @Override
  public Integer postUnLike(String authId, Long postId) {

    // 해당 유저가 해당 게시글에 좋아요 한 상태인지 확인
    validateNotLike(authId, postId);

    List<Object> result = redisTemplate.executePipelined((RedisCallback<?>) connection -> {

      StringRedisConnection redisConnection = (StringRedisConnection) connection;
      redisConnection.sRem(userPostLikeKey(authId), postId.toString());
      minusPostZSetVal(redisConnection, postId);
      // 반환할 좋아요 갯수 조회
      redisConnection.zScore(postLikeCountKey(), postId.toString());

      return null;
    });

    return Integer.valueOf((String) result.getLast());
  }

  private UserEntity findUserByAuthId(String authId) {
    return userRepository.findByAuthId(authId)
        .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
  }

  private PostEntity findPostById(Long postId) {
    return postRepository.findById(postId)
        .orElseThrow(() -> new CustomException(POST_NOT_FOUND));
  }

  private SetOperations<String, String> getSetOps() {
    return redisTemplate.opsForSet();
  }

  private void addPostInitialVal
      (StringRedisConnection redisConnection, String key, PostEntity savedPost) {
    redisConnection.zAdd(key, 0, savedPost.getId().toString());
  }

  private void plusPostZSetVal(StringRedisConnection redisConnection, Long postId) {
    redisConnection.zIncrBy(postViewCountKey(), 1, postId.toString());
  }

  private void minusPostZSetVal(StringRedisConnection redisConnection, Long postId) {
    redisConnection.zIncrBy(postLikeCountKey(), -1, postId.toString());
  }

  private void validateCreatedUser(UserEntity findUser, PostEntity findPost) {
    if (findUser != findPost.getCreateUser()) {
      throw new CustomException(POST_NOT_CREATE_USER);
    }
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
