package com.contest.grass.service;

import com.contest.grass.dto.SignUpRequestDto;
import com.contest.grass.entity.User;
import com.contest.grass.entity.EmailVerificationToken;
import com.contest.grass.exception.UserNotFoundException;
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
import java.util.*;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final EmailVerificationTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final String appUrl;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private Map<String, SignUpRequestDto> signUpRequests = new HashMap<>();  // 회원가입 정보를 임시 저장하는 Map

    // 생성자를 통한 의존성 주입 (중복 생성자 제거)
    @Autowired
    public UserService(UserRepository userRepository, EmailVerificationTokenRepository tokenRepository,
                       PasswordEncoder passwordEncoder, EmailService emailService,
                       @Value("${app.url}") String appUrl) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.appUrl = appUrl;
    }

    // 1. 이메일 존재 여부 확인
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    // userId를 통해 사용자의 프로필 정보를 가져오는 메서드
    public User getUserProfile(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다. ID: " + userId));
    }

    // 2. 이메일로 인증번호 발송
    public void sendVerificationCode(String email) {
        String verificationCode = generateVerificationCode(); // 6자리 인증번호 생성
        emailService.sendVerificationCode(email, verificationCode);

        // 임시로 저장해둔 SignUpRequestDto가 있을 경우, 인증번호를 저장
        SignUpRequestDto signUpRequest = signUpRequests.get(email);
        if (signUpRequest != null) {
            // User 객체를 조회하고 인증 토큰 생성 및 저장
            User user = userRepository.findByEmail(signUpRequest.getEmail())
                    .orElseThrow(() -> new UserNotFoundException("이메일에 해당하는 사용자를 찾을 수 없습니다: " + signUpRequest.getEmail()));

            // User 객체와 함께 토큰 생성
            EmailVerificationToken verificationToken = new EmailVerificationToken(
                    user,
                    verificationCode,
                    LocalDateTime.now().plusMinutes(10),  // 10분 만료
                    user.getEmail()
            );
            tokenRepository.save(verificationToken);
        }
    }

    // 6자리 인증번호 생성 메소드
    private String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000); // 6자리 숫자 생성
        return String.valueOf(code);
    }

    // 3. 회원가입 정보 임시 저장
    public void saveSignUpRequest(SignUpRequestDto signUpRequest) {
        signUpRequests.put(signUpRequest.getEmail(), signUpRequest);
    }

    // 4. 이메일 인증 처리 (DB 상태 변경, 트랜잭션 필요)
    @Transactional
    public boolean verifyEmailCode(String email, String code) {
        Optional<EmailVerificationToken> tokenOpt = tokenRepository.findByToken(code);

        if (tokenOpt.isPresent()) {
            EmailVerificationToken token = tokenOpt.get();
            // 로그 추가 - 저장된 토큰과 입력된 코드 출력
            logger.info("Stored token: " + token.getToken());
            logger.info("Input code: " + code);

            // 코드 일치 여부 및 만료 시간 확인
            if (token.getToken().equals(code) && token.getExpiresAt().isAfter(LocalDateTime.now())) {
                return true;
            }
        }

        return false;
    }

    // 5. 회원가입 최종 완료
    @Transactional
    public void completeSignUp(String email, String code) {
        if (!verifyEmailCode(email, code)) {
            throw new RuntimeException("Invalid verification code");
        }

        SignUpRequestDto signUpRequest = signUpRequests.get(email);

        if (signUpRequest != null) {
            User user = new User();
            user.setEmail(signUpRequest.getEmail());
            user.setName(signUpRequest.getName());
            user.setPassword(passwordEncoder.encode(signUpRequest.getPassword())); // 비밀번호 암호화
            user.setPhoneNumber(signUpRequest.getPhoneNumber());
            user.setVerified(true); // 이메일 인증 완료
            userRepository.save(user);

            // 인증이 완료된 사용자 요청 삭제
            signUpRequests.remove(email);
        }
    }

    // 6. 회원 삭제
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

    // 로그인 (읽기 전용, readOnly 트랜잭션 사용)
    @Transactional(readOnly = true)
    public User login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        return user;
    }

    // 이메일로 사용자 찾기
    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // 기존 사용자들의 비밀번호 암호화
    public void encryptExistingPasswords() {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            if (!isPasswordEncoded(user.getPassword())) {
                String encodedPassword = passwordEncoder.encode(user.getPassword());
                user.setPassword(encodedPassword);
                userRepository.save(user);  // 암호화된 비밀번호로 업데이트
            }
        }
    }

    // 비밀번호가 이미 암호화된 상태인지 확인하는 메소드
    private boolean isPasswordEncoded(String password) {
        return password.startsWith("$2a$");
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
    }

    public User updateNickname(Long id, String nickname) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setNickname(nickname);
            return userRepository.save(user);
        }

        throw new RuntimeException("User not found with id " + id);
    }
}