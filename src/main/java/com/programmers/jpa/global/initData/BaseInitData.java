package com.programmers.jpa.global.initData;

import com.programmers.jpa.domain.post.comment.entity.PostComment;
import com.programmers.jpa.domain.post.comment.service.PostCommentService;
import com.programmers.jpa.domain.post.post.entity.Post;
import com.programmers.jpa.domain.post.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class BaseInitData {
    private final PostService postService;
    private final PostCommentService postCommentService;
    @Autowired
    @Lazy
    private BaseInitData self;

    @Bean
    public ApplicationRunner baseInitDataApplicationRunner() {
        return args -> {
            self.work1();
            self.work2();
            self.work3();
        };
    }

    @Transactional
    public void work1() {
        if (postService.count() > 0) return;

        Post post1 = postService.write("title1", "content1");
        Post post2 = postService.write("title2", "content2");
        Post post3 = postService.write("title3", "content3");

        post1.setTitle("title1-1");

        post1.addComment(
                "comment1"
        );

        post1.addComment(
                "comment2"
        );

        post2.addComment(
                "comment3"
        );
    }

    @Transactional
    public void work2() {
        Post post = postService.findById(1).get();
        System.out.println("1번글 로드 완료");

        List<PostComment> postComments = post.getComments();
        System.out.println("1번글의 댓글들 로드 완료");

        PostComment postComment1 = postComments.get(0);
        System.out.println("1번글의 첫번째 댓글 로드 완료");

        PostComment postComment2 = postComments.get(1);
        System.out.println("1번글의 두번째 댓글 로드 완료");
    }

    @Transactional
    public void work3() {
        Post post1 = postService.findById(1).get(); // 즉시 SELECT 쿼리가 발생

        post1.getComments().size(); // 즉시 SELECT 쿼리가 발생

        post1.addComment("comment4"); // 위, 아래의 쿼리가 없다면 댓글들을 가져와서 comments 를 채우는 SELECT 없이 INSERT 쿼리만 발생, INSERT 쿼리는 트랜잭션이 종료되면 반영된다. 이 작업은 더티체킹이 아니라 PERSIST 에 의해서 이루어진다. PERSIST==INSERT, 더티체킹==UPDATE

        post1.getComments().get(2); // 이런경우에도 SELECT 쿼리가 발생
    }
}
