package com.health.api.controller;

import static org.springframework.http.HttpStatus.*;

import com.health.api.form.PostForm;
import com.health.api.service.ForumApplication;
import com.health.common.model.SuccessResponse;
import com.health.domain.dto.PostDomainDto;
import com.health.domain.response.PostResponse;
import com.health.security.authentication.AuthValidatorComponent;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/forums")
@RequiredArgsConstructor
public class ForumController {

  private final AuthValidatorComponent authValidatorComponent;
  private final ForumApplication forumApplication;


  @GetMapping("/posts")
  public ResponseEntity<?> getPostList(
      @CookieValue(name = "Authorization") String jwt, Pageable pageable
  ) {

    authValidatorComponent.validateAuthId(jwt);

    Page<PostDomainDto> postDomainDtoList = forumApplication.getPostList(pageable);

    return ResponseEntity.ok(
        SuccessResponse.of(postDomainDtoList.map(PostResponse.PostListResponse::fromDomainDto))
    );
  }

  @PostMapping("/posts")
  public ResponseEntity<?> createPost(
      @CookieValue(name = "Authorization") String jwt, @RequestBody @Validated PostForm postForm
  ) {

    String authId = authValidatorComponent.validateAuthId(jwt);

    PostDomainDto postDomainDto = forumApplication.createPost(authId, postForm);

    return ResponseEntity.status(CREATED).body(SuccessResponse.of(null));
  }

}
