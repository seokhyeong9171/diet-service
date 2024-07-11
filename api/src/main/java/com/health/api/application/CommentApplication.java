package com.health.api.application;

import com.health.api.form.CommentForm;
import com.health.service.forumservice.dto.CommentServiceDto;
import com.health.service.forumservice.service.CommentService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentApplication {

  private final CommentService commentService;

  public List<CommentServiceDto> getCommentList(Long postId) {
    return commentService.getCommentList(postId);
  }

  public CommentServiceDto createComment(String authId, Long postId, CommentForm commentForm) {
    return commentService.createComment(authId, postId, commentForm.toDomainForm());
  }

  public CommentServiceDto updateComment
      (String authId, Long postId, Long commentId, CommentForm commentForm) {
    return commentService.updateComment(authId, postId, commentId, commentForm.toDomainForm());
  }

  public CommentServiceDto createSubComment
      (String authId, Long postId, Long parentCommentId, CommentForm commentForm) {
    return commentService.createChildComment
        (authId, postId, parentCommentId, commentForm.toDomainForm());
  }

  public Long deleteComment(String authId, Long postId, Long commentId) {
    return commentService.deleteComment(authId, postId, commentId);
  }

  public CommentServiceDto updateChildComment
      (String authId, Long postId, Long commentId, Long childCommentId, CommentForm commentForm) {
    return commentService.updateChildComment
        (authId, postId, commentId, childCommentId, commentForm.toDomainForm());
  }

  public Long deleteChildComment
      (String authId, Long postId, Long parentCommentId, Long childCommentId) {
    return commentService.deleteChildComment(authId, postId, parentCommentId, childCommentId);
  }
}
