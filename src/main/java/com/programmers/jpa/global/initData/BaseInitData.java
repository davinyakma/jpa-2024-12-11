package com.programmers.jpa.global.initData;

import com.programmers.jpa.domain.post.comment.entity.PostComment;
import com.programmers.jpa.domain.post.comment.service.PostCommentService;
import com.programmers.jpa.domain.post.post.entity.Post;
import com.programmers.jpa.domain.post.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@RequiredArgsConstructor
public class BaseInitData {
    private final PostService postService;
    private final PostCommentService postCommentService;

    @Bean
    @Order(1)
    public ApplicationRunner baseInitData1ApplicationRunner() {
        return args -> {
            if (postService.count() > 0) return;

            Post post1 = postService.write("title1", "content1");
            Post post2 = postService.write("title2", "content2");
            Post post3 = postService.write("title3", "content3");

            // 1번글에 대한 댓글 1 생성
            PostComment postComment1 = postCommentService.write(post1, "comment1");
            // 1번글에 대한 댓글 2 생성
            PostComment postComment2 = postCommentService.write(post1, "comment2");

            // 2번글에 대한 댓글 3 생성
            PostComment postComment3 = postCommentService.write(post2, "comment3");

            Post postOfComment3 = postComment3.getPost();
        };
    }

    @Bean
    @Order(2)
    public ApplicationRunner baseInitData2ApplicationRunner() {
        return new ApplicationRunner() {
            @Transactional
            @Override
            public void run(ApplicationArguments args) throws Exception {
                PostComment postComment3 = postCommentService.findById(3).get();
                /*
                SELECT PC.*
                FROM post_comment AS PC
                WHERE PC.id = 3
                */

                Post postOfComment3 = postComment3.getPost();
                System.out.println("postOfComment3.id = " + postOfComment3.getId());
                System.out.println("postOfComment3.title = " + postOfComment3.getTitle());
                /*
                SELECT P.*
                FROM post AS P
                WHERE P.id = 2
                */

                System.out.println("postOfComment3.content = " + postOfComment3.getContent());
            }
        };
    }
}
