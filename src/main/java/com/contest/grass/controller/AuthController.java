package com.contest.grass.controller;

import com.contest.grass.config.JwtTokenUtil;
import com.contest.grass.dto.*;
import com.contest.grass.entity.GoogleUser;
import com.contest.grass.entity.KakaoUser;
import com.contest.grass.entity.User;
import com.contest.grass.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil; // JWT 유틸리티 클래스

    // Google 로그인
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
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
            }

            Optional<User> userOpt = userRepository.findByGoogleId(googleUser.getSub());
            User user = userOpt.orElseGet(() -> {
                User newUser = new User();
                newUser.setEmail(googleUser.getEmail());
                newUser.setGoogleId(googleUser.getSub());
                return userRepository.save(newUser);
            });

            // JWT 토큰 생성
            String jwtToken = jwtTokenUtil.generateToken(user.getEmail());

            return ResponseEntity.ok(new AuthResponse(jwtToken));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Authentication failed");
        }
    }

    // Kakao 로그인
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
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
            }

            Optional<User> userOpt = userRepository.findByKakaoId(kakaoUser.getId());
            User user = userOpt.orElseGet(() -> {
                User newUser = new User();
                newUser.setEmail(kakaoUser.getKakao_account().getEmail());
                newUser.setKakaoId(kakaoUser.getId());
                return userRepository.save(newUser);
            });

            // JWT 토큰 생성
            String jwtToken = jwtTokenUtil.generateToken(user.getEmail());

            return ResponseEntity.ok(new AuthResponse(jwtToken));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Authentication failed");
        }
    }

    // 일반 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequest) {
        Optional<User> userOpt = userRepository.findByEmail(loginRequest.getEmail());

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                // JWT 토큰 생성
                String jwtToken = jwtTokenUtil.generateToken(user.getEmail());
                return ResponseEntity.ok(new AuthResponse(jwtToken));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequestDto signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
        }

        User user = new User();
        user.setEmail(signUpRequest.getEmail());
        user.setName(signUpRequest.getName());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setPhoneNumber(signUpRequest.getPhoneNumber());

        userRepository.save(user);
        // 회원가입 후 바로 로그인 처리 -> JWT 토큰 생성
        String jwtToken = jwtTokenUtil.generateToken(user.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(new AuthResponse(jwtToken));
    }

    // 아이디 찾기
    @PostMapping("/find-id")
    public ResponseEntity<?> findId(@RequestBody FindIdRequestDto findIdRequest) {
        Optional<User> userOpt = userRepository.findByPhoneNumber(findIdRequest.getPhoneNumber());

        if (userOpt.isPresent()) {
            return ResponseEntity.ok(userOpt.get().getEmail());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }

    // 비밀번호 찾기
    @PostMapping("/find-password")
    public ResponseEntity<?> findPassword(@RequestBody FindPasswordRequestDto findPasswordRequest) {
        Optional<User> userOpt = userRepository.findByPhoneNumber(findPasswordRequest.getPhoneNumber());

        if (userOpt.isPresent()) {
            // 비밀번호 변경 링크 또는 임시 비밀번호 발송 로직을 추가할 수 있습니다.
            return ResponseEntity.ok("Password reset link sent");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }
}
