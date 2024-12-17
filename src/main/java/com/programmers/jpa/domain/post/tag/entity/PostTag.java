package com.programmers.jpa.domain.post.tag.entity;

import com.programmers.jpa.domain.post.post.entity.Post;
import com.programmers.jpa.global.jpa.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostTag extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @Column(length = 30)
    private String content;
}