package com.contest.grass.repository;

import com.contest.grass.entity.EmailVerificationToken;
import com.contest.grass.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface EmailVerificationTokenRepository extends JpaRepository<EmailVerificationToken, Long> {
    Optional<EmailVerificationToken> findByToken(String token);
    Optional<EmailVerificationToken> findByUser(User user);
    @Transactional
    void deleteByUserId(Long userId);
    Optional<EmailVerificationToken> findByEmail(String email);
}