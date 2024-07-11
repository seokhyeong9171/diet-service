package com.health.api.controller;

import static org.springframework.http.HttpStatus.CREATED;

import com.health.api.form.CommentForm;
import com.health.api.application.CommentApplication;
import com.health.api.model.SuccessResponse;
import com.health.service.forumservice.dto.CommentServiceDto;
import com.health.service.forumservice.response.CommentListResponse;
import com.health.service.forumservice.response.CommentListResponse.CommentInfo;
import com.health.security.authentication.AuthValidatorComponent;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/forum/posts/{postId}/comments")
@RequiredArgsConstructor
public class CommentController {

  private final AuthValidatorComponent authValidatorComponent;
  private final CommentApplication commentApplication;

  @GetMapping
  public ResponseEntity<?> getCommentList(
      @CookieValue("Authorization") String jwt, @PathVariable Long postId
  ) {
    authValidatorComponent.validateAuthId(jwt);

    List<CommentServiceDto> commentDomainDtoList = commentApplication.getCommentList(postId);

    return ResponseEntity.ok(
        SuccessResponse.of(commentDomainDtoList.stream().map(CommentListResponse::fromDomainDto))
    );
  }

  @PostMapping
  public ResponseEntity<?> createComment(
      @CookieValue("Authorization") String jwt, @PathVariable Long postId,
      @RequestBody @Validated CommentForm commentForm
  ) {
    String authId = authValidatorComponent.validateAuthId(jwt);

    CommentServiceDto commentDomainDto =
        commentApplication.createComment(authId, postId, commentForm);

    return ResponseEntity.status(CREATED).body(
        SuccessResponse.of(CommentInfo.fromDomainDto(commentDomainDto))
    );
  }

  @PatchMapping("/{commentId}")
  public ResponseEntity<?> updateComment(
      @CookieValue("Authorization") String jwt,
      @PathVariable Long postId, @PathVariable Long commentId,
      @RequestBody @Validated CommentForm commentForm
  ) {
    String authId = authValidatorComponent.validateAuthId(jwt);

    CommentServiceDto commentDomainDto =
        commentApplication.updateComment(authId, postId, commentId, commentForm);

    return ResponseEntity.status(CREATED).body(
        SuccessResponse.of(CommentInfo.fromDomainDto(commentDomainDto))
    );
  }

  @DeleteMapping("/{commentId}")
  public ResponseEntity<?> deleteComment(
      @CookieValue("Authorization") String jwt,
      @PathVariable Long postId, @PathVariable Long commentId
  ) {

    String authId = authValidatorComponent.validateAuthId(jwt);

    Long deletedCommentId = commentApplication.deleteComment(authId, postId, commentId);

    return ResponseEntity.ok(SuccessResponse.of("deleted commentId: " + deletedCommentId));
  }



  @PostMapping("/{commentId}")
  public ResponseEntity<?> createChildComment(
      @CookieValue("Authorization") String jwt,
      @PathVariable Long postId, @PathVariable Long commentId,
      @RequestBody @Validated CommentForm commentForm
  ) {
    String authId = authValidatorComponent.validateAuthId(jwt);

    CommentServiceDto commentDomainDto =
        commentApplication.createSubComment(authId, postId, commentId, commentForm);

    return ResponseEntity.status(CREATED).body(
        SuccessResponse.of(CommentListResponse.fromDomainDto(commentDomainDto))
    );
  }

  @PatchMapping("/{commentId}/{childCommentId}")
  public ResponseEntity<?> updateChildComment(
      @CookieValue("Authorization") String jwt,
      @PathVariable Long postId, @PathVariable Long commentId, @PathVariable Long childCommentId,
      @RequestBody @Validated CommentForm commentForm
  ) {

    String authId = authValidatorComponent.validateAuthId(jwt);

    CommentServiceDto commentDomainDto =
        commentApplication.updateChildComment(authId, postId, commentId, childCommentId, commentForm);

    return ResponseEntity.ok(
        SuccessResponse.of(CommentInfo.fromDomainDto(commentDomainDto))
    );
  }

  @DeleteMapping("/{commentId}/{childCommentId}")
  public ResponseEntity<?> deleteChildComment(
      @CookieValue("Authorization") String jwt,
      @PathVariable Long postId, @PathVariable Long commentId, @PathVariable Long childCommentId
  ) {

    String authId = authValidatorComponent.validateAuthId(jwt);

    Long deletedChildCommentId =
        commentApplication.deleteChildComment(authId, postId, commentId, childCommentId);

    return ResponseEntity.ok(SuccessResponse.of("deleted commentId: " + deletedChildCommentId));
  }




}
