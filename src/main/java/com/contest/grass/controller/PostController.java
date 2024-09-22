package com.contest.grass.controller;

import com.contest.grass.dto.PostDto;
import com.contest.grass.entity.Post;
import com.contest.grass.entity.PostStatus;
import com.contest.grass.entity.User;
import com.contest.grass.repository.PostRepository;
import com.contest.grass.repository.UserRepository;
import com.contest.grass.service.PostService;
import com.contest.grass.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;


@RestController
@RequestMapping("/api/posts")
@Tag(name = "Post", description = "게시물 관리 API")
public class PostController {

    private final PostService postService;
    private final UserService userService;

    private final PostRepository postRepository;


    @Autowired
    public PostController(PostService postService, UserService userService, PostRepository postRepository) {
        this.postService = postService;
        this.userService = userService;

        this.postRepository = postRepository;

    }

    @Operation(summary = "모든 게시물 조회", description = "모든 게시물의 닉네임, 내용, 좋아요 수를 조회")
    @GetMapping
    public List<PostDto> getAllPosts() {
        return postService.getAllPosts();
    }

    @Operation(summary = "특정 게시물 조회", description = "특정 ID를 가진 게시물을 조회")
    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPost(@PathVariable Long postId) {
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            PostDto postDto = new PostDto();
            postDto.setPostId(post.getPostId());
            postDto.setContent(post.getContent());
            postDto.setIsPublic(post.getIsPublic());
            postDto.setNickname(post.getNickname());
            postDto.setCreatedAt(String.valueOf(post.getCreatedAt()));
            if (post.getImageData() != null) {
                postDto.setImageData(Base64.getEncoder().encodeToString(post.getImageData()));
            }
            return ResponseEntity.ok(postDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "게시물 생성" , description = "게시물 생성")
    @PostMapping
    public ResponseEntity<Post> createPost(@RequestParam("content") String content,
                                           @RequestParam("isPublic") String isPublic,
                                           @RequestParam("userId") Long userId,
                                           @RequestParam("nickname") String nickname,
                                           @RequestParam("file") MultipartFile file) {
        try {
            // 파일 처리
            byte[] imageData = file.getBytes();
            String imageName = file.getOriginalFilename();
            String imageType = file.getContentType();

            // 유저 정보 조회
            User user = userService.getUserById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // 게시물 생성
            Post post = new Post();
            post.setContent(content);
            post.setIsPublic(isPublic);
            post.setUser(user);
            post.setNickname(nickname);
            post.setImageData(imageData);
            post.setImageName(imageName);
            post.setImageType(imageType);
            post.setCreatedAt(LocalDateTime.now());
            post.setStatus(PostStatus.PUBLIC);

            // 저장
            Post savedPost = postRepository.save(post);

            return ResponseEntity.ok(savedPost);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
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


    @Operation(summary = "마지막 게시물 ID 조회", description = "마지막 게시물의 ID를 조회")
    @GetMapping("/last-id")
    public ResponseEntity<Long> getLastPostId() {
        Long lastPostId = postService.findLastPostId();
        return ResponseEntity.ok(lastPostId);
    }
}
