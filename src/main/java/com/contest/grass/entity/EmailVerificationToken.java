package com.contest.grass.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class EmailVerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // User 객체와의 Many-to-One 관계 유지
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private String email;

    // 기본 생성자
    public EmailVerificationToken() {}

    // 생성자: User 객체를 사용하여 토큰 생성
    public EmailVerificationToken(User user, String token, LocalDateTime expiresAt, String email) {
        this.user = user;
        this.token = token;
        this.expiresAt = expiresAt;
        this.email = email;
        this.createdAt = LocalDateTime.now();
    }

    // 토큰 만료 여부 확인
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(this.expiresAt);
    }
}
