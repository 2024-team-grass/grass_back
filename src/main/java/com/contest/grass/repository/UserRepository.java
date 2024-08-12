package com.contest.grass.repository;

import com.contest.grass.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByGoogleId(String googleId);
    User findByKakaoId(String kakaoId);
}