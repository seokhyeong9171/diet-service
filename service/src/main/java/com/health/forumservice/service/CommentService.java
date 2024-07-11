package com.health.forumservice.service;

import com.health.forumservice.dto.CommentServiceDto;
import com.health.forumservice.form.CommentServiceForm;
import java.util.List;

public interface CommentService {

  List<CommentServiceDto> getCommentList(Long postId);

  CommentServiceDto createComment(String authId, Long postId, CommentServiceForm serviceForm);

  CommentServiceDto createChildComment
      (String authId, Long postId, Long parentCommentId, CommentServiceForm serviceForm);

  CommentServiceDto updateComment
      (String authId, Long postId, Long commentId, CommentServiceForm serviceForm);

  Long deleteComment(String authId, Long postId, Long commentId);

  CommentServiceDto updateChildComment(
      String authId, Long postId,
      Long parentCommentId, Long childCommentId, CommentServiceForm serviceForm
  );

  Long deleteChildComment(String authId, Long postId, Long parentCommentId, Long childCommentId);
}
