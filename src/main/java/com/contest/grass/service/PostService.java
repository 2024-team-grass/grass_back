package com.contest.grass.service;

import com.contest.grass.dto.PostDto;
import com.contest.grass.entity.Post;
import com.contest.grass.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // 1. 모든 게시물 조회
    public List<PostDto> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(post -> {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            String formattedCreatedAt = post.getCreatedAt() != null ? post.getCreatedAt().format(formatter) : null;

            String imageDataBase64 = post.getImageData() != null ? Base64.getEncoder().encodeToString(post.getImageData()) : null;

            return new PostDto(
                    post.getPostId(),
                    post.getNickname(),
                    post.getContent(),
                    post.getGoodbtn(),
                    imageDataBase64,
                    post.getIsPublic(),
                    formattedCreatedAt
            );
        }).collect(Collectors.toList());
    }

    // 게시물 생성시 이미지 저장
    public Post createPostWithImage(Post post, MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            post.setImageData(file.getBytes());
            post.setImageName(file.getOriginalFilename());
            post.setImageType(file.getContentType());
        }
        return postRepository.save(post);
    }

    // 2. 게시물 ID로 조회
    public Post getPostById(Long postId) {
        Optional<Post> post = postRepository.findById(postId);
        return post.orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));
    }

    // 3. 게시물 생성
    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    // 4. 게시물 삭제
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    // 5. 게시물 좋아요 증가
    public Post incrementLike(Long id) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isPresent()) {
            Post existingPost = post.get();
            existingPost.setGoodbtn(existingPost.getGoodbtn() + 1);
            return postRepository.save(existingPost); // 업데이트 후 저장
        }
        throw new RuntimeException("Post not found with id: " + id);
    }

    // 6. 게시물 좋아요 감소
    public Post decrementLike(Long id) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isPresent()) {
            Post existingPost = post.get();
            existingPost.setGoodbtn(existingPost.getGoodbtn() - 1);
            return postRepository.save(existingPost); // 업데이트 후 저장
        }
        throw new RuntimeException("Post not found with id: " + id);
    }

    // 7. 추가 게시물 로드 (오프셋 및 제한)
    public List<Post> getMorePosts(int offset, int limit) {
        List<Post> allPosts = postRepository.findAllByOrderByPostIdAsc();
        // 수동으로 오프셋과 제한을 적용 (페이징)
        int toIndex = Math.min(offset + limit, allPosts.size());
        return allPosts.subList(offset, toIndex);
    }

    // 8. 업데이트된 게시물 확인
    public List<Post> getUpdatedPosts(Long lastPostId) {
        return postRepository.findByPostIdGreaterThan(lastPostId);
    }

    // 9. 포스트 마지막 아이디 찾기
    public Long findLastPostId() {
        Post post = postRepository.findFirstByOrderByPostIdDesc();
        return post != null ? post.getPostId() : null;
    }

    // 게시물 ID로 조회 (이미지와 함께)
    public Post getPostByIdWithImages(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));
    }

    // 11. 게시물 저장
    public Post savePost(Post post) {
        return postRepository.save(post);
    }
}
