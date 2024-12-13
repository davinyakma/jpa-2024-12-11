package com.programmers.jpa.domain.post.post.repository;

import com.programmers.jpa.domain.post.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
