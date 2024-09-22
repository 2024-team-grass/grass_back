package com.contest.grass.controller;

import com.contest.grass.config.JwtTokenUtil;
import com.contest.grass.dto.*;
import com.contest.grass.entity.EmailVerificationToken;
import com.contest.grass.entity.User;
import com.contest.grass.repository.EmailVerificationTokenRepository;
import com.contest.grass.repository.UserRepository;
import com.contest.grass.service.PostService;
import com.contest.grass.service.SmsService;
import com.contest.grass.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/user")
@Tag(name = "User Management", description = "유저 관리 및 인증 API") // Swagger 태그 추가
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private SmsService smsService;

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

    @Autowired
    private ObjectMapper objectMapper;

    // 이메일 인증 번호 조회
    public Optional<EmailVerificationToken> findByUser(User user) {
        return tokenRepository.findByUser(user);  // EmailVerificationToken 조회
    }

    @Transactional
    public void deleteToken(EmailVerificationToken token) {
        tokenRepository.delete(token);  // 토큰 삭제
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

                AuthResponse authResponse = new AuthResponse(
                        jwtToken,
                        user.getName(),
                        user.getNickname(),
                        user.getSprouts(),
                        user.getId()
                );
                return ResponseEntity.ok(authResponse);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("잘못된 자격 증명: 비밀번호가 일치하지 않습니다.");
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자를 찾을 수 없습니다.");
    }

    // 회원가입(이메일 인증번호 발송)
    @Operation(summary = "회원가입", description = "새 사용자를 등록, 이메일 인증 완료 시 회원 추가")
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequestDto signUpRequest) {
        if (userService.emailExists(signUpRequest.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이메일이 이미 존재합니다.");
        }

        // 이메일로 인증번호 발송
        userService.sendVerificationCode(signUpRequest.getEmail());

        // 사용자 정보 임시 저장
        userService.saveSignUpRequest(signUpRequest);

        return ResponseEntity.ok("이메일로 인증번호를 발송했습니다.");
    }

    // 이메일 OTP 인증
    @Operation(summary = "이메일 인증", description = "이메일 인증을 처리")
    @PostMapping("/verify-code")
    public ResponseEntity<String> verifyEmailCode(@RequestParam String email, @RequestParam String code) {
        boolean isVerified = userService.verifyEmailCode(email, code); // 인증번호 검증

        if (isVerified) {
            // 회원가입을 완료합니다.
            userService.completeSignUp(email, code);

            // JWT 토큰 생성 및 응답
            String jwtToken = jwtTokenUtil.generateToken(email);
            return ResponseEntity.ok(new String(jwtToken));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("인증코드가 만료되었거나 일치하지 않습니다.");
        }
    }

    // SMS로 인증 코드 발송
    @Operation(summary = "SMS 인증 코드 발송", description = "사용자의 전화번호로 인증 코드를 발송합니다.")
    @PostMapping("/send-sms-code")
    public ResponseEntity<String> sendSmsCode(@RequestParam String phoneNumber) {
        userService.sendPhoneVerificationCode(phoneNumber);
        return ResponseEntity.ok("SMS로 인증 코드가 전송되었습니다.");
    }

    // SMS 인증 코드 검증
    @Operation(summary = "SMS 인증 코드 검증", description = "사용자가 입력한 SMS 인증 코드를 검증합니다.")
    @PostMapping("/verify-sms-code")
    public ResponseEntity<String> verifySmsCode(@RequestParam String phoneNumber, @RequestParam String code) {
        boolean isVerified = userService.verifyPhoneCode(phoneNumber, code);

        if (isVerified) {
            return ResponseEntity.ok("전화번호 인증이 완료되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("인증 코드가 일치하지 않거나 만료되었습니다.");
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
        UserDto userProfile = userService.getUserProfileWithPosts(userId);
        return ResponseEntity.ok(userProfile);
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

    @PostMapping("/{userId}/sprouts")
    public ResponseEntity<?> updateUserSprouts(@PathVariable Long userId, @RequestBody Map<String, String> payload) {
        Optional<User> userOpt = userRepository.findById(userId);

        if (userOpt.isPresent()) {
            User user = userOpt.get();

            // 문자열 포인트 값을 가져와서 숫자로 변환
            String incomingSproutsStr = payload.get("sprouts");
            Integer incomingSprouts = Integer.parseInt(incomingSproutsStr.replace(",", ""));

            // 기존 포인트 값을 가져와서 문자열로 변환
            String currentSproutsStr = user.getSprouts();
            Integer currentSprouts = currentSproutsStr != null ? Integer.parseInt(currentSproutsStr.replace(",", "")) : 0;

            // 포인트 업데이트
            Integer updatedSprouts = currentSprouts + 10;

            // 숫자를 문자열로 변환 (콤마 추가)
            String updatedSproutsStr = String.format("%,d", updatedSprouts);

            // 업데이트된 포인트를 저장
            user.setSprouts(updatedSproutsStr);
            userRepository.save(user);

            // 현재 포인트를 JSON 형식으로 반환
            return ResponseEntity.ok(Collections.singletonMap("sprouts", updatedSproutsStr));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자를 찾을 수 없습니다.");
        }
    }

    @Operation(summary = "자동 로그인", description = "JWT 토큰을 검증하여 자동 로그인")
    @PostMapping("/verify-token")
    public ResponseEntity<?> verifyToken(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            // Authorization 헤더에서 "Bearer " 접두어를 제거
            String token = authorizationHeader.substring(7);

            // 토큰 검증
            boolean isValid = jwtTokenUtil.validateToken(token);

            if (isValid) {
                // 유효한 토큰일 경우 이메일 추출
                String email = jwtTokenUtil.getUsernameFromToken(token);
                Optional<User> optionalUser = userService.findByEmail(email);

                if (optionalUser.isPresent()) {
                    User user = optionalUser.get();
                    // TokenDto에 유효성 및 이메일 정보 설정
                    return ResponseEntity.ok().body(new TokenDto(isValid, email));
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자를 찾을 수 없습니다.");
                }
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
            }
        } catch (Exception e) {
            // 예외 처리 및 로그
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }

    @PutMapping("/{userId}/updateNickname")
    public ResponseEntity<?> updateNickname(@PathVariable Long userId, @RequestBody Map<String, String> payload) {
        // 사용자 조회
        Optional<User> userOpt = userRepository.findById(userId);

        if (userOpt.isPresent()) {
            User user = userOpt.get();

            // 닉네임 업데이트
            String newNickname = payload.get("nickname");
            if (newNickname == null || newNickname.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("닉네임이 제공되지 않았습니다.");
            }

            user.setNickname(newNickname);
            userRepository.save(user);

            // 성공 응답 반환
            return ResponseEntity.ok(Collections.singletonMap("message", "닉네임이 성공적으로 업데이트되었습니다."));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자를 찾을 수 없습니다.");
        }
    }
}