package com.contest.grass.repository;

import com.contest.grass.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    Optional<User> findByNickname(String nickname); // 닉네임 중복 체크를 위한 메서드
    Optional<User> findByPhoneNumber(String phoneNumber);

    Optional<User> findByGoogleId(String googleId);
    Optional<User> findByKakaoId(String kakaoId);

    boolean existsByEmail(String email);
}