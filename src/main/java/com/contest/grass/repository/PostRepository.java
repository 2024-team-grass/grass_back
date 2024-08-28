package com.contest.grass.repository;

import com.contest.grass.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    // 기본적으로 JpaRepository에서 제공하는 CRUD 메서드를 사용할 수 있습니다.

    // 추가 게시물을 불러오기 위한 메서드
    List<Post> findByPostIdGreaterThan(Long lastPostId);

    // 오프셋과 제한을 사용한 게시물 페칭
    List<Post> findAllByOrderByPostIdAsc(); // 필요에 따라 오프셋과 제한을 수동으로 처리
}