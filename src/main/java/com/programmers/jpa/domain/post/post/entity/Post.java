package com.programmers.jpa.domain.post.post.entity;

import com.programmers.jpa.domain.member.member.entity.Member;
import com.programmers.jpa.domain.post.comment.entity.PostComment;
import com.programmers.jpa.global.jpa.entity.BaseTime;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Post extends BaseTime {
    @ManyToOne(fetch = FetchType.LAZY)
    private Member author;

    @Column(length = 100)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private boolean blind;

    @Builder.Default
    @OneToMany(mappedBy = "post", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<PostComment> comments = new ArrayList<>();

    public void addComment(Member author, String content) {
        PostComment postComment = PostComment
                .builder()
                .post(this)
                .author(author)
                .content(content)
                .build();

        comments.add(postComment);
    }

    public boolean removeComment(PostComment comment) {
        return comments.remove(comment);
    }
}