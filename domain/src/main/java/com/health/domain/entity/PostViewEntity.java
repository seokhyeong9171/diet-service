package com.health.domain.entity;

import static lombok.AccessLevel.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "post_view")
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Getter @Builder
public class PostViewEntity {

  @Id @GeneratedValue
  @Column(name = "post_view_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private UserEntity viewedUser;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "post_id")
  private PostEntity post;

  public static PostViewEntity createNew(UserEntity viewedUser, PostEntity post) {
    return PostViewEntity.builder()
        .viewedUser(viewedUser).post(post)
        .build();
  }

}
