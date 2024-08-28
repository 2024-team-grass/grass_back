package com.contest.grass.controller;

import com.contest.grass.entity.Post;
import com.contest.grass.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 1. 닉네임, 내용, 좋아요 조회 (GET)
    @GetMapping
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    // 2. 특정 게시물 조회 (GET)
    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPostById(@PathVariable Long postId) {
        Post post = postService.getPostById(postId);
        return ResponseEntity.ok(post);
    }

    // 3. 게시물 생성 (POST)
    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        Post newPost = postService.createPost(post);
        return ResponseEntity.ok(newPost);
    }

    // 4. 게시물 삭제 (DELETE)
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }

    // 5. 좋아요 클릭 (POST)
    @PostMapping("/{id}/like")
    public ResponseEntity<Post> likePost(@PathVariable Long id) {
        Post updatedPost = postService.incrementLike(id);
        return ResponseEntity.ok(updatedPost);
    }

    // 6. 좋아요 취소 (POST)
    @PostMapping("/{id}/unlike")
    public ResponseEntity<Post> unlikePost(@PathVariable Long id) {
        Post updatedPost = postService.decrementLike(id);
        return ResponseEntity.ok(updatedPost);
    }

    // 7. 맨 아래 스크롤 시 게시물 추가 로드 (GET)
    @GetMapping("/more")
    public List<Post> getMorePosts(@RequestParam int offset, @RequestParam int limit) {
        return postService.getMorePosts(offset, limit);
    }

    // 8. 맨 위로 스크롤 시 게시물 업데이트 확인 (GET)
    @GetMapping("/updates")
    public List<Post> getUpdatedPosts(@RequestParam Long lastPostId) {
        return postService.getUpdatedPosts(lastPostId);
    }
}