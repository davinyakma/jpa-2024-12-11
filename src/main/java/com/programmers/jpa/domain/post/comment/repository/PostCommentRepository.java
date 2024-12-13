package com.programmers.jpa.domain.post.comment.repository;

import com.programmers.jpa.domain.post.comment.entity.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCommentRepository extends JpaRepository<PostComment, Long> {
}
