package com.contest.grass.controller;

import com.contest.grass.entity.User;
import com.contest.grass.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User", description = "Operations related to user mangement")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 1. 로그인 (GET)
    @GetMapping("/login")
    public ResponseEntity<User> login(@RequestParam String email, @RequestParam String password) {
        User user = userService.login(email, password);
        return ResponseEntity.ok(user);
    }

    // 2. 회원가입 (POST)
    @PostMapping("/register")
    public ResponseEntity<User> register(
            @RequestParam String email,
            @RequestParam String name,
            @RequestParam String password,
            @RequestParam String confirmPassword,
            @RequestParam String phoneNumber) {
        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException("Passwords do not match");
        }
        User newUser = userService.register(email, name, password, phoneNumber);
        return ResponseEntity.ok(newUser);
    }

    // 3. 아이디 찾기 (POST)
    @PostMapping("/find-id")
    public ResponseEntity<String> findId(@RequestParam String phoneNumber) {
        String email = userService.findEmailByPhoneNumber(phoneNumber);
        return ResponseEntity.ok(email);
    }

    // 4. 비밀번호 찾기 (POST)
    @PostMapping("/find-password")
    public ResponseEntity<String> findPassword(@RequestParam String phoneNumber) {
        String tempPassword = userService.sendTemporaryPassword(phoneNumber);
        return ResponseEntity.ok("Temporary password sent: " + tempPassword);
    }

    // 5. 비밀번호 재설정 (POST)
    @PostMapping("/reset-password")
    public ResponseEntity<User> resetPassword(
            @RequestParam String email,
            @RequestParam String newPassword,
            @RequestParam String confirmPassword) {
        if (!newPassword.equals(confirmPassword)) {
            throw new IllegalArgumentException("Passwords do not match");
        }
        User updatedUser = userService.resetPassword(email, newPassword);
        return ResponseEntity.ok(updatedUser);
    }

    // 6. 마이페이지에서 사용자 정보와 사용자가 작성한 게시물을 조회하는 엔드포인트 (GET)
    @GetMapping("/{userId}/mypage")
    public ResponseEntity<User> getUserProfile(@PathVariable Long userId) {
        User user = userService.getUserProfile(userId);
        return ResponseEntity.ok(user);
    }

    // 이메일 인증 API
    @GetMapping("/verify-email")
    public ResponseEntity<String> verifyEmail(@RequestParam("token") String token) {
        boolean isVerified = userService.verifyEmail(token);

        if (isVerified) {
            return ResponseEntity.ok("Email verified successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or expired token.");
        }
    }
}

