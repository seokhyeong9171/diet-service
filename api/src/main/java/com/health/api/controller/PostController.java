package com.health.api.controller;

import static com.health.service.forumservice.response.PostResponse.PostContentResponse;
import static com.health.service.forumservice.response.PostResponse.PostListResponse;
import static org.springframework.http.HttpStatus.CREATED;

import com.health.api.application.PostApplication;
import com.health.api.form.PostForm;
import com.health.api.model.SuccessResponse;
import com.health.security.authentication.AuthValidatorComponent;
import com.health.service.forumservice.dto.PostServiceDto;
import com.health.service.forumservice.response.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
@RequestMapping("/forums/posts")
@RequiredArgsConstructor
public class PostController {

  private final AuthValidatorComponent authValidatorComponent;
  private final PostApplication postApplication;


  @GetMapping
  public ResponseEntity<?> getPostList(Pageable pageable) {

    Page<PostServiceDto> postDomainDtoList = postApplication.getPostList(pageable);

    return ResponseEntity.ok(
        SuccessResponse.of(postDomainDtoList.map(PostListResponse::fromDomainDto))
    );
  }

  @PostMapping
  public ResponseEntity<?> createPost(
      @CookieValue(name = "Authorization") String jwt, @RequestBody @Validated PostForm postForm
  ) {

    String authId = authValidatorComponent.validateAuthId(jwt);

    PostServiceDto postServiceDto = postApplication.createPost(authId, postForm);

    return ResponseEntity.status(CREATED).body(
        SuccessResponse.of(PostContentResponse.fromDomainDto(postServiceDto))
    );
  }

  @PatchMapping("/{postId}")
  public ResponseEntity<?> updatePost(
      @CookieValue(name = "Authorization") String jwt,
      @PathVariable Long postId, @RequestBody @Validated PostForm postForm
  ) {

    String authId = authValidatorComponent.validateAuthId(jwt);

    PostServiceDto postServiceDto = postApplication.updatePost(authId, postId, postForm);

    return ResponseEntity.ok(
        SuccessResponse.of(PostContentResponse.fromDomainDto(postServiceDto))
    );
  }

  @DeleteMapping("/{postId}")
  public ResponseEntity<?> deletePost(
      @CookieValue(name = "Authorization") String jwt, @PathVariable Long postId
  ) {

    String authId = authValidatorComponent.validateAuthId(jwt);

    Long deletedPostId = postApplication.deletePost(authId, postId);

    return ResponseEntity.ok(SuccessResponse.of(deletedPostId));
  }

  @GetMapping("/{postId}/like")
  public ResponseEntity<?> getPostLikeCount(@PathVariable Long postId) {

    return ResponseEntity.ok(
        SuccessResponse.of(postApplication.getPostLikeCount(postId))
    );
  }

  @GetMapping("/{postId}/view")
  public ResponseEntity<?> getPostViewCount(@PathVariable Long postId) {

    return ResponseEntity.ok(
        SuccessResponse.of(postApplication.getPostViewCount(postId))
    );
  }

  @GetMapping("/{postId}")
  public ResponseEntity<?> getPostInfo(
      @CookieValue(name = "Authorization") String jwt, @PathVariable Long postId
  ) {

    String authId = authValidatorComponent.validateAuthId(jwt);
    PostServiceDto postServiceDto = postApplication.getPostInfo(authId, postId);

    return ResponseEntity.ok(
        SuccessResponse.of(PostResponse.PostContentResponse.fromDomainDto(postServiceDto))
    );
  }

  @PostMapping("/{postId}/like")
  public ResponseEntity<?> postAddLike(
      @CookieValue(name = "Authorization") String jwt, @PathVariable Long postId
  ) {

    String authId = authValidatorComponent.validateAuthId(jwt);
    Integer likeCount = postApplication.postAddLike(authId, postId);

    return ResponseEntity.ok(SuccessResponse.of(likeCount));
  }

  @PatchMapping("/{postId}/like")
  public ResponseEntity<?> postUnLike(
      @CookieValue(name = "Authorization") String jwt, @PathVariable Long postId
  ) {

    String authId = authValidatorComponent.validateAuthId(jwt);
    Integer likeCount = postApplication.postUnLike(authId, postId);

    return ResponseEntity.ok(SuccessResponse.of(likeCount));
  }


}
