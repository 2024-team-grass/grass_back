package com.contest.grass.controller;

import com.contest.grass.entity.Post;
import com.contest.grass.entity.User;
import com.contest.grass.service.PostService;
import com.contest.grass.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@Tag(name = "Post", description = "게시물 관리 API") // Swagger 태그 추가
public class PostController {

    private final PostService postService;
    private final UserService userService;

    @Autowired
    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService; // UserService 의존성 주입
    }

    @Operation(summary = "모든 게시물 조회", description = "모든 게시물의 닉네임, 내용, 좋아요 수를 조회")
    @GetMapping
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @Operation(summary = "특정 게시물 조회", description = "특정 ID를 가진 게시물을 조회")
    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPostById(@PathVariable Long postId) {
        Post post = postService.getPostById(postId);
        return ResponseEntity.ok(post);
    }

    @Operation(summary = "게시물 생성", description = "새로운 게시물을 생성")
    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post, @RequestParam Long userId) {
        // userId를 통해 User 객체를 찾음
        User user = userService.getUserById(userId); // UserService에서 userId로 사용자 검색
        post.setUser(user); // Post에 user 설정
        Post newPost = postService.createPost(post);
        return ResponseEntity.ok(newPost);
    }

    @Operation(summary = "게시물 삭제", description = "특정 ID를 가진 게시물을 삭제")
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "좋아요 클릭", description = "특정 게시물에 좋아요를 추가")
    @PostMapping("/{id}/like")
    public ResponseEntity<Post> likePost(@PathVariable Long id) {
        Post updatedPost = postService.incrementLike(id);
        return ResponseEntity.ok(updatedPost);
    }

    @Operation(summary = "좋아요 취소", description = "특정 게시물에 대한 좋아요를 취소")
    @PostMapping("/{id}/unlike")
    public ResponseEntity<Post> unlikePost(@PathVariable Long id) {
        Post updatedPost = postService.decrementLike(id);
        return ResponseEntity.ok(updatedPost);
    }

    @Operation(summary = "게시물 추가 로드", description = "스크롤을 맨 아래로 내릴 시 추가 게시물을 로드")
    @GetMapping("/more")
    public List<Post> getMorePosts(@RequestParam int offset, @RequestParam int limit) {
        return postService.getMorePosts(offset, limit);
    }

    @Operation(summary = "게시물 업데이트 확인", description = "스크롤을 맨 위로 올릴 시 새로운 게시물이 있는지 확인")
    @GetMapping("/updates")
    public List<Post> getUpdatedPosts(@RequestParam Long lastPostId) {
        return postService.getUpdatedPosts(lastPostId);
    }
}
