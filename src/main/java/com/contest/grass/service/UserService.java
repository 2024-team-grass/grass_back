package com.contest.grass.service;

import com.contest.grass.entity.User;
import com.contest.grass.entity.EmailVerificationToken;
import com.contest.grass.repository.UserRepository;
import com.contest.grass.repository.EmailVerificationTokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    private EmailVerificationTokenRepository tokenRepository;

    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final String appUrl;  // 앱의 기본 URL
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);


    @Autowired
    public UserService(UserRepository userRepository, EmailVerificationTokenRepository tokenRepository,
                       PasswordEncoder passwordEncoder, EmailService emailService,
                       @Value("${app.url}") String appUrl) {  // appUrl 주입
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.appUrl = appUrl;  // 주입받은 URL 저장
    }

    // 1. 회원가입 처리 및 이메일 인증번호 발송
    @Transactional
    public User register(String email, String name, String password, String phoneNumber) {
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setPassword(passwordEncoder.encode(password)); // 비밀번호 암호화
        user.setPhoneNumber(phoneNumber);

        String nickname = generateRandomNickname();
        while (userRepository.findByNickname(nickname).isPresent()) {
            nickname = generateRandomNickname(); // 중복 시 다시 생성
        }
        user.setNickname(nickname);
        user.setSprouts(0);
        user.setVerified(false);
        User savedUser = userRepository.save(user);

        String verificationCode = generateVerificationCode(); // 6자리 인증번호 생성
        EmailVerificationToken verificationToken = new EmailVerificationToken(savedUser, verificationCode, LocalDateTime.now().plusMinutes(10)); // 10분 만료
        tokenRepository.save(verificationToken);

        emailService.sendVerificationCode(savedUser.getEmail(), verificationCode);

        return savedUser;
    }

    // 6자리 인증번호 생성 메소드
    private String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000); // 6자리 숫자 생성
        return String.valueOf(code);
    }

    // 회원 삭제
    @Transactional
    public boolean deleteUserByEmail(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            tokenRepository.deleteByUserId(user.getId());
            userRepository.delete(user);
            return true;
        } else {
            return false;
        }
    }

    // 랜덤 닉네임 생성 로직
    private String generateRandomNickname() {
        String[] randomWords = {"Sky", "Ocean", "Star", "Mountain", "River"};
        Random random = new Random();
        return randomWords[random.nextInt(randomWords.length)] + random.nextInt(1000);
    }


    // 2. 이메일 인증 처리 (DB 상태 변경, 트랜잭션 필요)
    @Transactional
    public boolean verifyEmailCode(String email, String code) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            return false; // 사용자가 존재하지 않음
        }

        User user = userOpt.get();
        Optional<EmailVerificationToken> tokenOpt = tokenRepository.findByUser(user);  // tokenRepository를 통해 호출

        if (tokenOpt.isEmpty() || !tokenOpt.get().getToken().equals(code) || tokenOpt.get().getExpiresAt().isBefore(LocalDateTime.now())) {
            return false; // 인증번호가 일치하지 않거나 만료됨
        }

        user.setVerified(true); // 인증 성공
        userRepository.save(user);
        tokenRepository.delete(tokenOpt.get()); // 인증 완료된 토큰 삭제

        return true;
    }

    // 3. 로그인 (읽기 전용, readOnly 트랜잭션 사용)
    @Transactional(readOnly = true)
    public User login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        return user;
    }

    // 4. 이메일로 사용자 찾기 (읽기 전용)
    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // 5. 소셜 ID로 사용자 찾기 (Google, 읽기 전용)
    @Transactional(readOnly = true)
    public Optional<User> findByGoogleId(String googleId) {
        return userRepository.findByGoogleId(googleId);
    }

    // 6. 소셜 ID로 사용자 찾기 (Kakao, 읽기 전용)
    @Transactional(readOnly = true)
    public Optional<User> findByKakaoId(String kakaoId) {
        return userRepository.findByKakaoId(kakaoId);
    }

    // 7. 전화번호로 사용자 찾기 (읽기 전용)
    @Transactional(readOnly = true)
    public Optional<User> findByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }

    // 8. 비밀번호 변경 처리 (DB 상태 변경, 트랜잭션 필요)
    @Transactional
    public User changePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        return userRepository.save(user);
    }

    // 9. 아이디 찾기 (전화번호로 이메일 찾기, 읽기 전용)
    @Transactional(readOnly = true)
    public String findEmailByPhoneNumber(String phoneNumber) {
        Optional<User> user = userRepository.findByPhoneNumber(phoneNumber);
        return user.map(User::getEmail).orElseThrow(() -> new RuntimeException("User not found"));
    }

    // 10. 비밀번호 찾기 (임시 비밀번호 발송, DB 상태 변경, 트랜잭션 필요)
    @Transactional
    public String sendTemporaryPassword(String phoneNumber) {
        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new RuntimeException("User not found"));
        String tempPassword = UUID.randomUUID().toString().substring(0, 8);
        user.setPassword(passwordEncoder.encode(tempPassword));
        userRepository.save(user);
        // 실제로는 이 임시 비밀번호를 사용자에게 SMS나 이메일로 보내야 함
        return tempPassword;
    }

    // 11. 비밀번호 재설정 (DB 상태 변경, 트랜잭션 필요)
    @Transactional
    public User resetPassword(String email, String newPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setPassword(passwordEncoder.encode(newPassword));
        return userRepository.save(user);
    }

    // 12. 마이페이지 조회 (읽기 전용)
    @Transactional(readOnly = true)
    public User getUserProfile(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
    }
    // 기존 사용자들의 비밀번호 암호화
    public void encryptExistingPasswords() {
        List<User> users = userRepository.findAll();  // 모든 사용자 조회
        for (User user : users) {
            if (!isPasswordEncoded(user.getPassword())) {
                // 이미 암호화된 비밀번호가 아니라면 암호화
                String encodedPassword = passwordEncoder.encode(user.getPassword());
                user.setPassword(encodedPassword);
                userRepository.save(user);  // 암호화된 비밀번호로 업데이트
            }
        }
    }
    // 비밀번호가 이미 암호화된 상태인지 확인하는 메소드 (BCrypt 비밀번호는 "$2a$"로 시작)
    private boolean isPasswordEncoded(String password) {
        return password.startsWith("$2a$");
    }
}
