package com.contest.grass.controller;

import com.contest.grass.config.JwtTokenUtil;
import com.contest.grass.dto.*;
import com.contest.grass.entity.EmailVerificationToken;
import com.contest.grass.entity.GoogleUser;
import com.contest.grass.entity.KakaoUser;
import com.contest.grass.entity.User;
import com.contest.grass.repository.EmailVerificationTokenRepository;
import com.contest.grass.repository.UserRepository;
import com.contest.grass.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/user")
@Tag(name = "User Management", description = "유저 관리 및 인증 API") // Swagger 태그 추가
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil; // JWT 유틸리티 클래스
    @Autowired
    private EmailVerificationTokenRepository tokenRepository;

    public Optional<EmailVerificationToken> findByUser(User user) {
        return tokenRepository.findByUser(user);  // EmailVerificationToken 조회
    }

    @Transactional
    public void deleteToken(EmailVerificationToken token) {
        tokenRepository.delete(token);  // 토큰 삭제
    }


    // 구글 로그인
    @Operation(summary = "Google 로그인", description = "Google OAuth2를 통해 사용자 인증을 수행")
    @PostMapping("/google")
    public ResponseEntity<?> authenticateWithGoogle(@RequestBody String token) {
        try {
            WebClient webClient = webClientBuilder.build();
            GoogleUser googleUser = webClient.get()
                    .uri("https://www.googleapis.com/oauth2/v3/tokeninfo?id_token={token}", token)
                    .retrieve()
                    .bodyToMono(GoogleUser.class)
                    .block();

            if (googleUser == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 토큰입니다.");
            }

            Optional<User> userOpt = userRepository.findByGoogleId(googleUser.getSub());
            User user = userOpt.orElseGet(() -> {
                User newUser = new User();
                newUser.setEmail(googleUser.getEmail());
                newUser.setGoogleId(googleUser.getSub());
                return userRepository.save(newUser);
            });

            String jwtToken = jwtTokenUtil.generateToken(user.getEmail());
            return ResponseEntity.ok(new AuthResponse(jwtToken));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("인증 실패");
        }
    }

    // 카카오 로그인
    @Operation(summary = "Kakao 로그인", description = "Kakao OAuth2를 통해 사용자 인증을 수행")
    @PostMapping("/kakao")
    public ResponseEntity<?> authenticateWithKakao(@RequestBody String token) {
        try {
            WebClient webClient = webClientBuilder.build();
            KakaoUser kakaoUser = webClient.get()
                    .uri("https://kapi.kakao.com/v2/user/me")
                    .header("Authorization", "Bearer " + token)
                    .retrieve()
                    .bodyToMono(KakaoUser.class)
                    .block();

            if (kakaoUser == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 토큰입니다.");
            }

            Optional<User> userOpt = userRepository.findByKakaoId(kakaoUser.getId());
            User user = userOpt.orElseGet(() -> {
                User newUser = new User();
                newUser.setEmail(kakaoUser.getKakao_account().getEmail());
                newUser.setKakaoId(kakaoUser.getId());
                return userRepository.save(newUser);
            });

            String jwtToken = jwtTokenUtil.generateToken(user.getEmail());
            return ResponseEntity.ok(new AuthResponse(jwtToken));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("인증 실패");
        }
    }

    // 일반 로그인
    @Operation(summary = "일반 로그인", description = "이메일과 비밀번호를 사용하여 로그인")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequest) {
        Optional<User> userOpt = userRepository.findByEmail(loginRequest.getEmail());

        if (userOpt.isPresent()) {
            User user = userOpt.get();

            if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                String jwtToken = jwtTokenUtil.generateToken(user.getEmail());
                return ResponseEntity.ok(new AuthResponse(jwtToken));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("잘못된 자격 증명: 비밀번호가 일치하지 않습니다.");
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자를 찾을 수 없습니다.");
    }

    // 회원가입
    @Transactional
    @Operation(summary = "회원가입", description = "새 사용자를 등록하고 JWT 토큰을 생성")
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequestDto signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이메일이 이미 존재합니다.");
        }

        // UserService의 register 메서드 호출
        User user = userService.register(
                signUpRequest.getEmail(),
                signUpRequest.getName(),
                signUpRequest.getPassword(),
                signUpRequest.getPhoneNumber()
        );

        // JWT 토큰 생성 및 응답
        String jwtToken = jwtTokenUtil.generateToken(user.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(new AuthResponse(jwtToken));
    }

    // 이메일 OTP 인증
    @Operation(summary = "이메일 인증", description = "이메일 인증을 처리")
    @PostMapping("/verify-code")
    public ResponseEntity<String> verifyEmailCode(@RequestParam String email, @RequestParam String code) {
        boolean isVerified = userService.verifyEmailCode(email, code); // 수정된 부분
        if (isVerified) {
            return ResponseEntity.ok("Email verified successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or expired code");
        }
    }


    // 아이디 찾기
    @Transactional(readOnly = true)
    @Operation(summary = "아이디 찾기", description = "사용자의 전화번호를 이용해 이메일을 찾음")
    @PostMapping("/find-id")
    public ResponseEntity<?> findId(@RequestBody FindIdRequestDto findIdRequest) {
        Optional<User> userOpt = userRepository.findByPhoneNumber(findIdRequest.getPhoneNumber());

        if (userOpt.isPresent()) {
            return ResponseEntity.ok(userOpt.get().getEmail());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자를 찾을 수 없습니다.");
    }


    // 비밀번호 찾기
    @Transactional(readOnly = true)
    @Operation(summary = "비밀번호 찾기", description = "사용자의 전화번호를 이용해 비밀번호 재설정 링크를 발송")
    @PostMapping("/find-password")
    public ResponseEntity<?> findPassword(@RequestBody FindPasswordRequestDto findPasswordRequest) {
        Optional<User> userOpt = userRepository.findByPhoneNumber(findPasswordRequest.getPhoneNumber());

        if (userOpt.isPresent()) {
            return ResponseEntity.ok("비밀번호 재설정 링크가 전송되었습니다.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자를 찾을 수 없습니다.");
    }


    // 비밀번호 재설정
    @Transactional
    @Operation(summary = "비밀번호 재설정", description = "새 비밀번호로 사용자의 비밀번호를 재설정")
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String email, @RequestParam String newPassword, @RequestParam String confirmPassword) {
        if (!newPassword.equals(confirmPassword)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("비밀번호가 일치하지 않습니다.");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        // 비밀번호 암호화 전에 이미 암호화된 비밀번호인지 확인
        if (!newPassword.startsWith("$2a$")) {
            user.setPassword(passwordEncoder.encode(newPassword));
        } else {
            user.setPassword(newPassword); // 이미 암호화된 상태라면 그대로 저장
        }

        userRepository.save(user);
        return ResponseEntity.ok("비밀번호가 성공적으로 변경되었습니다.");
    }

    // 사용자 정보 및 게시물 조회
    @Operation(summary = "사용자 정보 및 게시물 조회", description = "사용자의 프로필 정보와 그 사용자가 작성한 게시물을 조회")
    @GetMapping("/{userId}/mypage")
    public ResponseEntity<UserDto> getUserProfile(@PathVariable Long userId) {
        User user = userService.getUserProfile(userId);
        return ResponseEntity.ok(user.toDto()); // 간단하게 변환
    }

    // 회원 삭제
    @Operation(summary = "회원 삭제", description = "이메일을 통해 회원을 삭제")
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestParam String email) {
        try {
            boolean isDeleted = userService.deleteUserByEmail(email);
            if (isDeleted) {
                return ResponseEntity.ok("사용자가 성공적으로 삭제되었습니다.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자를 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("사용자 삭제 중 오류가 발생했습니다.");
        }
    }

    // 기존 사용자들의 비밀번호를 암호화하는 로직
    @Transactional
    public void encryptExistingPasswords() {
        List<User> users = userRepository.findAll();  // 모든 사용자 조회
        for (User user : users) {
            // 비밀번호가 암호화되지 않았는지 확인
            if (!isPasswordEncoded(user.getPassword())) {
                // 비밀번호가 암호화되지 않았다면 암호화
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
