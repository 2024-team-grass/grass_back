package com.contest.grass.controller;

import com.contest.grass.config.JwtTokenUtil;
import com.contest.grass.dto.*;
import com.contest.grass.entity.GoogleUser;
import com.contest.grass.entity.KakaoUser;
import com.contest.grass.entity.User;
import com.contest.grass.repository.UserRepository;
import com.contest.grass.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

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
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("잘못된 자격 증명");
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자를 찾을 수 없습니다.");
    }

    // 회원가입
    @Operation(summary = "회원가입", description = "새 사용자를 등록하고 JWT 토큰을 생성")
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequestDto signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이메일이 이미 존재합니다.");
        }

        User user = new User();
        user.setEmail(signUpRequest.getEmail());
        user.setName(signUpRequest.getName());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setPhoneNumber(signUpRequest.getPhoneNumber());

        userRepository.save(user);
        String jwtToken = jwtTokenUtil.generateToken(user.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(new AuthResponse(jwtToken));
    }

    // 아이디 찾기
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
    @Operation(summary = "비밀번호 재설정", description = "새 비밀번호로 사용자의 비밀번호를 재설정")
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String email, @RequestParam String newPassword, @RequestParam String confirmPassword) {
        if (!newPassword.equals(confirmPassword)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("비밀번호가 일치하지 않습니다.");
        }

        User updatedUser = userService.resetPassword(email, newPassword);
        return ResponseEntity.ok(updatedUser);
    }

    // 사용자 프로필 및 게시물 조회
    @Operation(summary = "사용자 정보 및 게시물 조회", description = "사용자의 프로필 정보와 그 사용자가 작성한 게시물을 조회")
    @GetMapping("/{userId}/mypage")
    public ResponseEntity<User> getUserProfile(@PathVariable Long userId) {
        User user = userService.getUserProfile(userId);
        return ResponseEntity.ok(user);
    }

    // 이메일 인증
    @Operation(summary = "이메일 인증", description = "이메일 인증을 처리")
    @GetMapping("/verify-email")
    public ResponseEntity<String> verifyEmail(@RequestParam("token") String token) {
        boolean isVerified = userService.verifyEmail(token);

        if (isVerified) {
            return ResponseEntity.ok("이메일이 성공적으로 인증되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("유효하지 않거나 만료된 토큰입니다.");
        }
    }
}
