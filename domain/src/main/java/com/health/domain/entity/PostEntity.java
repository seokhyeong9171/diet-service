package com.health.domain.entity;

import com.health.domain.form.PostDomainForm;
import com.health.domain.type.PostCategory;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity(name = "post")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class PostEntity extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "category")
    private PostCategory postCategory;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "likes")
    private int like;

    @Column(name = "views")
    private int view;

    @Column(name = "create_dt")
    private LocalDateTime postCreateDt;
    @Column(name = "update_dt")
    private LocalDateTime postUpdateDt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity createUser;

    @OneToMany(mappedBy = "post")
    private List<CommentEntity> commentList = new ArrayList<>();

    public static PostEntity createFromForm(UserEntity user, PostDomainForm form) {
        return PostEntity.builder()
            .postCategory(form.getPostCategory())
            .title(form.getTitle())
            .content(form.getContent())
            .like(0)
            .view(0)
            .postCreateDt(LocalDateTime.now())
            .createUser(user)
            .build();
    }

    public void updateFromForm(PostDomainForm form) {
        this.title = form.getTitle();
        this.content = form.getContent();
        this.postUpdateDt = LocalDateTime.now();
    }



    public void updateLike(int like) {
        this.like = like;
    }
}
