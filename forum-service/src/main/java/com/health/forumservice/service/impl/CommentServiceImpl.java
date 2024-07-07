package com.health.forumservice.service.impl;

import static com.health.common.exception.ErrorCode.*;

import com.health.common.exception.CustomException;
import com.health.domain.dto.CommentDomainDto;
import com.health.domain.entity.CommentEntity;
import com.health.domain.entity.PostEntity;
import com.health.domain.entity.UserEntity;
import com.health.domain.form.CommentDomainForm;
import com.health.domain.repository.CommentRepository;
import com.health.domain.repository.PostRepository;
import com.health.domain.repository.UserRepository;
import com.health.forumservice.service.CommentService;
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
  public List<CommentDomainDto> getCommentList(Long postId) {

    // 오래된 순서대로 정렬
    return getPostByPostId(postId).getCommentList().stream()
        .filter(c -> !c.getIsDeleted())
        .sorted(Comparator.comparing(CommentEntity::getCreatedDt))
        .map(CommentDomainDto::fromEntity)
        .toList();
  }

  @Override
  public CommentDomainDto createComment(String authId, Long postId, CommentDomainForm domainForm) {

    UserEntity findUser = getUserByAuthId(authId);
    PostEntity findPost = getPostByPostId(postId);

    CommentEntity createdComment = CommentEntity.createComment(findPost, findUser, domainForm);
    CommentEntity savedComment = commentRepository.save(createdComment);
    findPost.getCommentList().add(savedComment);

    return CommentDomainDto.fromEntity(savedComment);
  }

  @Override
  public CommentDomainDto updateComment
      (String authId, Long postId, Long commentId, CommentDomainForm domainForm) {

    UserEntity findUser = getUserByAuthId(authId);
    PostEntity findPost = getPostByPostId(postId);
    CommentEntity findComment = getCommentByCommentId(commentId);

    // 해당 댓글을 작성한 유저인지 확인
    validateWrittenUser(findUser, findComment);
    validateDeleted(findComment);
    validateRightPost(findPost, findComment);

    findComment.updateComment(domainForm);

    return CommentDomainDto.fromEntity(findComment);
  }

  @Override
  public Long deleteComment(String authId, Long postId, Long commentId) {
    UserEntity findUser = getUserByAuthId(authId);
    PostEntity findPost = getPostByPostId(postId);
    CommentEntity findComment = getCommentByCommentId(commentId);

    validateWrittenUser(findUser, findComment);
    validateDeleted(findComment);
    validateRightPost(findPost, findComment);

    // child comment 없는 경우엔 comment 삭제
    // child comment 있는 경우엔 delete flag만 변경
    if (findComment.getChildCommentList().isEmpty()) {
      findPost.getCommentList().remove(findComment);
      commentRepository.delete(findComment);
    } else {
      findComment.markDeleteFlag();
    }

    return findComment.getId();
  }

  @Override
  public CommentDomainDto createChildComment
      (String authId, Long postId, Long parentCommentId, CommentDomainForm domainForm) {

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
        CommentEntity.createSubComment(findParentComment, findUser, domainForm);
    CommentEntity savedChildComment = commentRepository.save(createdChildComment);
    findParentComment.getChildCommentList().add(savedChildComment);

    return CommentDomainDto.fromEntity(savedChildComment);
  }

  @Override
  public CommentDomainDto updateChildComment(
      String authId, Long postId,
      Long parentCommentId, Long childCommentId, CommentDomainForm domainForm
  ) {

    UserEntity findUser = getUserByAuthId(authId);
    PostEntity findPost = getPostByPostId(postId);
    CommentEntity findParentComment = getCommentByCommentId(parentCommentId);
    CommentEntity findChildComment = getCommentByCommentId(childCommentId);

    validateWrittenUser(findUser, findChildComment);
    validateRightPost(findPost, findParentComment);
    validateAccurateParent(findParentComment, findChildComment);

    findChildComment.updateComment(domainForm);

    return null;
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

    findParentComment.getChildCommentList().remove(findChildComment);
    commentRepository.delete(findChildComment);

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
    if (findComment.getIsDeleted()) {
      throw new CustomException(COMMENT_PARENT_DELETED);
    }
  }

  private void validateThisIsParent(CommentEntity findComment) {
    if (findComment.getParent() != null) {
      throw new CustomException(COMMENT_IS_SUB);
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
