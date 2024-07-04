package com.health.forumservice.service;

import com.health.domain.dto.PostDomainDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {

  Page<PostDomainDto> getPostList(Pageable pageable);
}
