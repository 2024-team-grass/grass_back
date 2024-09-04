package com.contest.grass.service;

import com.contest.grass.entity.User;
import com.contest.grass.entity.EmailVerificationToken;
import com.contest.grass.repository.UserRepository;
import com.contest.grass.repository.EmailVerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final EmailVerificationTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Autowired
    public UserService(UserRepository userRepository, EmailVerificationTokenRepository tokenRepository,
                       PasswordEncoder passwordEncoder, EmailService emailService) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    // 1. 회원가입 처리 및 이메일 인증 발송
    @Transactional
    public User register(String email, String name, String password, String phoneNumber) {
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setPassword(passwordEncoder.encode(password)); // 비밀번호 암호화
        user.setPhoneNumber(phoneNumber);
        user.setNickname(UUID.randomUUID().toString().substring(0, 8)); // 임시 닉네임 생성
        user.setSprouts(0); // 새싹 초기값
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

    // 2. 이메일 인증 처리
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

    // 3. 로그인
    public User login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        return user;
    }

    // 4. 이메일로 사용자 찾기
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // 5. 소셜 ID로 사용자 찾기 (Google)
    public Optional<User> findByGoogleId(String googleId) {
        return userRepository.findByGoogleId(googleId);
    }

    // 6. 소셜 ID로 사용자 찾기 (Kakao)
    public Optional<User> findByKakaoId(String kakaoId) {
        return userRepository.findByKakaoId(kakaoId);
    }

    // 7. 전화번호로 사용자 찾기
    public Optional<User> findByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }

    // 8. 비밀번호 변경 처리
    @Transactional
    public User changePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        return userRepository.save(user);
    }

    // 9. 아이디 찾기 (전화번호로 이메일 찾기)
    public String findEmailByPhoneNumber(String phoneNumber) {
        Optional<User> user = userRepository.findByPhoneNumber(phoneNumber);
        return user.map(User::getEmail).orElseThrow(() -> new RuntimeException("User not found"));
    }

    // 10. 비밀번호 찾기 (임시 비밀번호 발송)
    public String sendTemporaryPassword(String phoneNumber) {
        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new RuntimeException("User not found"));
        String tempPassword = UUID.randomUUID().toString().substring(0, 8);
        user.setPassword(passwordEncoder.encode(tempPassword));
        userRepository.save(user);
        // 실제로는 이 임시 비밀번호를 사용자에게 SMS나 이메일로 보내야 함
        return tempPassword;
    }

    // 11. 비밀번호 재설정
    @Transactional
    public User resetPassword(String email, String newPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setPassword(passwordEncoder.encode(newPassword));
        return userRepository.save(user);
    }

    // 12. 마이페이지 조회
    public User getUserProfile(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
    }
}
