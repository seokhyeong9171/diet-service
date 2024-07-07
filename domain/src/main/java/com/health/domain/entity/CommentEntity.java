package com.health.domain.entity;

import com.health.domain.form.CommentDomainForm;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.OrderBy;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity(name = "comment")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class CommentEntity extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private PostEntity post;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity createdUser;

    @Column(name = "delete_yn")
    private Boolean isDeleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private CommentEntity parent;

    @OneToMany(mappedBy = "parent")
    @OrderBy("createdDt ASC")
    private List<CommentEntity> childCommentList = new ArrayList<>();

    public static CommentEntity createComment
        (PostEntity post, UserEntity user, CommentDomainForm form) {

        return CommentEntity.builder()
            .content(form.getContent())
            .post(post)
            .createdUser(user)
            .isDeleted(false)
            .build();
    }

    public static CommentEntity createChildComment
        (CommentEntity comment, UserEntity user, CommentDomainForm form) {

        return CommentEntity.builder()
            .content(form.getContent())
            .post(comment.getPost())
            .createdUser(user)
            .isDeleted(false)
            .parent(comment)
            .build();
    }

    public void updateComment(CommentDomainForm form) {
        this.content = form.getContent();
    }



    public void markDeleteFlag() {
        this.isDeleted = true;
    }
}
