package com.health.service.forumservice.service.impl;

import static com.health.domain.exception.ErrorCode.COMMENT_AND_POST_NOT_MATCH;
import static com.health.domain.exception.ErrorCode.COMMENT_AND_USER_NOT_MATCH;
import static com.health.domain.exception.ErrorCode.COMMENT_CHILD_AND_PARENT_NOT_MATCH;
import static com.health.domain.exception.ErrorCode.COMMENT_IS_CHILD;
import static com.health.domain.exception.ErrorCode.COMMENT_NOT_FOUND;
import static com.health.domain.exception.ErrorCode.COMMENT_PARENT_DELETED;
import static com.health.domain.exception.ErrorCode.POST_NOT_FOUND;
import static com.health.domain.exception.ErrorCode.USER_NOT_FOUND;

import com.health.domain.entity.CommentEntity;
import com.health.domain.entity.PostEntity;
import com.health.domain.entity.UserEntity;
import com.health.domain.exception.CustomException;
import com.health.domain.repository.CommentRepository;
import com.health.domain.repository.PostRepository;
import com.health.domain.repository.UserRepository;
import com.health.service.forumservice.dto.CommentServiceDto;
import com.health.service.forumservice.form.CommentServiceForm;
import com.health.service.forumservice.service.CommentService;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

  private final UserRepository userRepository;
  private final PostRepository postRepository;
  private final CommentRepository commentRepository;

  @Override
  @Transactional(readOnly = true)
  public List<CommentServiceDto> getCommentList(Long postId) {

    // 오래된 순서대로 정렬
    return getPostByPostId(postId).getCommentList().stream()
        .filter(c -> !c.isDeleted())
        .sorted(Comparator.comparing(CommentEntity::getCreatedDt))
        .map(CommentServiceDto::fromEntity)
        .toList();
  }

  @Override
  public CommentServiceDto createComment(String authId, Long postId, CommentServiceForm serviceForm) {

    UserEntity findUser = getUserByAuthId(authId);
    PostEntity findPost = getPostByPostId(postId);

    CommentEntity createdComment = CommentEntity.createComment(findPost, findUser, serviceForm.toDomainForm());
    CommentEntity savedComment = commentRepository.save(createdComment);
    findPost.getCommentList().add(savedComment);

    return CommentServiceDto.fromEntity(savedComment);
  }

  @Override
  public CommentServiceDto updateComment
      (String authId, Long postId, Long commentId, CommentServiceForm serviceForm) {

    UserEntity findUser = getUserByAuthId(authId);
    PostEntity findPost = getPostByPostId(postId);
    CommentEntity findComment = getCommentByCommentId(commentId);

    // 해당 댓글을 작성한 유저인지 확인
    validateWrittenUser(findUser, findComment);
    validateDeleted(findComment);
    validateRightPost(findPost, findComment);

    findComment.updateComment(serviceForm.toDomainForm());

    return CommentServiceDto.fromEntity(findComment);
  }

  @Override
  public Long deleteComment(String authId, Long postId, Long commentId) {
    UserEntity findUser = getUserByAuthId(authId);
    PostEntity findPost = getPostByPostId(postId);
    CommentEntity findComment = getCommentByCommentId(commentId);

    validateWrittenUser(findUser, findComment);
    validateDeleted(findComment);
    validateRightPost(findPost, findComment);

    // flag 변경으로 soft delete
    findComment.markDeleteFlag();

    return findComment.getId();
  }

  @Override
  public CommentServiceDto createChildComment
      (String authId, Long postId, Long parentCommentId, CommentServiceForm serviceForm) {

    UserEntity findUser = getUserByAuthId(authId);
    PostEntity findPost = getPostByPostId(postId);
    CommentEntity findParentComment = getCommentByCommentId(parentCommentId);

    // 상위 댓글 삭제된 상태인지 확인
    validateDeleted(findParentComment);
    // 해당 댓글이 상위 댓글인지 확인
    validateThisIsParent(findParentComment);
    // 해당 댓글이 달린 게시글인지 확인
    validateRightPost(findPost, findParentComment);

    CommentEntity createdChildComment =
        CommentEntity.createChildComment(findParentComment, findUser, serviceForm.toDomainForm());
    CommentEntity savedChildComment = commentRepository.save(createdChildComment);
    findParentComment.getChildCommentList().add(savedChildComment);

    return CommentServiceDto.fromEntity(savedChildComment);
  }

  @Override
  public CommentServiceDto updateChildComment(
      String authId, Long postId,
      Long parentCommentId, Long childCommentId, CommentServiceForm serviceForm
  ) {

    UserEntity findUser = getUserByAuthId(authId);
    PostEntity findPost = getPostByPostId(postId);
    CommentEntity findParentComment = getCommentByCommentId(parentCommentId);
    CommentEntity findChildComment = getCommentByCommentId(childCommentId);

    validateWrittenUser(findUser, findChildComment);
    validateRightPost(findPost, findParentComment);
    validateAccurateParent(findParentComment, findChildComment);

    findChildComment.updateComment(serviceForm.toDomainForm());

    return CommentServiceDto.fromEntity(findChildComment);
  }

  @Override
  public Long deleteChildComment
      (String authId, Long postId, Long parentCommentId, Long childCommentId) {

    UserEntity findUser = getUserByAuthId(authId);
    PostEntity findPost = getPostByPostId(postId);
    CommentEntity findParentComment = getCommentByCommentId(parentCommentId);
    CommentEntity findChildComment = getCommentByCommentId(childCommentId);

    validateWrittenUser(findUser, findChildComment);
    validateRightPost(findPost, findParentComment);
    validateAccurateParent(findParentComment, findChildComment);

    // flag 변경으로 soft delete
    findChildComment.markDeleteFlag();

    return findChildComment.getId();
  }

  private void validateRightPost(PostEntity findPost, CommentEntity findComment) {
    if (findPost != findComment.getPost()) {
      throw new CustomException(COMMENT_AND_POST_NOT_MATCH);
    }
  }

  private UserEntity getUserByAuthId(String authId) {
    return userRepository.findByAuthId(authId)
        .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
  }

  private PostEntity getPostByPostId(Long postId) {
    return postRepository.findById(postId)
        .orElseThrow(() -> new CustomException(POST_NOT_FOUND));
  }

  private CommentEntity getCommentByCommentId(Long commentId) {
    return commentRepository.findById(commentId)
        .orElseThrow(() -> new CustomException(COMMENT_NOT_FOUND));
  }

  private void validateDeleted(CommentEntity findComment) {
    if (findComment.isDeleted()) {
      throw new CustomException(COMMENT_PARENT_DELETED);
    }
  }

  private void validateThisIsParent(CommentEntity findComment) {
    if (findComment.getParent() != null) {
      throw new CustomException(COMMENT_IS_CHILD);
    }
  }

  private void validateAccurateParent(CommentEntity parentComment, CommentEntity childComment) {
    if (parentComment != childComment.getParent()) {
      throw new CustomException(COMMENT_CHILD_AND_PARENT_NOT_MATCH);
    }
  }

  private void validateWrittenUser(UserEntity findUser, CommentEntity findComment) {
    if (findUser != findComment.getCreatedUser()) {
      throw new CustomException(COMMENT_AND_USER_NOT_MATCH);
    }
  }
}
