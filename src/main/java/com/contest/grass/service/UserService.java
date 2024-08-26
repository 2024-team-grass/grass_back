package com.contest.grass.service;

import com.contest.grass.entity.User;
import com.contest.grass.repository.UserRepository;
import com.contest.grass.entity.EmailVerificationToken;
import com.contest.grass.repository.EmailVerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailVerificationTokenRepository tokenRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService; // 이메일 서비스 추가

    // 회원가입 처리 및 이메일 인증 발송
    @Transactional
    public User registerUser(User user) {
        // 비밀번호 암호화
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setIsVerified(false); // 이메일 인증 상태를 false로 초기화
        User savedUser = userRepository.save(user);

        // 이메일 인증 토큰 생성 및 저장
        String token = UUID.randomUUID().toString();
        EmailVerificationToken verificationToken = new EmailVerificationToken(savedUser, token, LocalDateTime.now().plusHours(24));
        tokenRepository.save(verificationToken);

        // 이메일 발송
        emailService.sendVerificationEmail(savedUser.getEmail(), token);

        return savedUser;
    }

    // 이메일 인증 처리
    @Transactional
    public boolean verifyEmail(String token) {
        Optional<EmailVerificationToken> verificationTokenOpt = tokenRepository.findByToken(token);

        if (verificationTokenOpt.isEmpty()) {
            return false; // 토큰이 존재하지 않음
        }

        EmailVerificationToken verificationToken = verificationTokenOpt.get();

        // 토큰이 만료되었는지 확인
        if (verificationToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            return false; // 토큰 만료
        }

        // 사용자 인증 처리
        User user = verificationToken.getUser();
        user.setIsVerified(true); // 이메일 인증 완료
        userRepository.save(user);

        // 인증이 완료된 토큰 삭제
        tokenRepository.delete(verificationToken);

        return true; // 이메일 인증 성공
    }

    // 이메일로 사용자 찾기
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // 소셜 ID로 사용자 찾기 (Google)
    public Optional<User> findByGoogleId(String googleId) {
        return userRepository.findByGoogleId(googleId);
    }

    // 소셜 ID로 사용자 찾기 (Kakao)
    public Optional<User> findByKakaoId(String kakaoId) {
        return userRepository.findByKakaoId(kakaoId);
    }

    // 전화번호로 사용자 찾기
    public Optional<User> findByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }

    // 비밀번호 변경 처리
    @Transactional
    public User changePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        return userRepository.save(user);
    }
}
