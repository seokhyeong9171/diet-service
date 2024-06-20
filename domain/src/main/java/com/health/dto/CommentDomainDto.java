package com.health.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDomainDto {

    private Long id;

    private String content;

    private PostDomainDto post;

    private UserDomainDto createdUser;
}
