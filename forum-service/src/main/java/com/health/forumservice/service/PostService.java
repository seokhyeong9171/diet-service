package com.health.forumservice.service;

import com.health.domain.dto.PostDomainDto;
import com.health.domain.form.PostDomainForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {

  Page<PostDomainDto> getPostList(Pageable pageable);

  PostDomainDto createPost(String authId, PostDomainForm form);

  PostDomainDto updatePost(String authId, Long postId, PostDomainForm domainForm);

  Long deletePost(String authId, Long postId);

  Integer getPostLikeValue(Long postId);
}
