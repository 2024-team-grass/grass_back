package com.contest.grass.controller;

import com.contest.grass.entity.User;
import com.contest.grass.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User", description = "Operations related to user management") // Swagger 태그 추가
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "로그인", description = "이메일과 비밀번호를 사용하여 사용자를 로그인")
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestParam String email, @RequestParam String password) {
        User user = userService.login(email, password);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "회원가입", description = "새로운 사용자를 등록")
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

    @Operation(summary = "아이디 찾기", description = "사용자의 전화번호를 이용하여 이메일을 찾음")
    @PostMapping("/find-id")
    public ResponseEntity<String> findId(@RequestParam String phoneNumber) {
        String email = userService.findEmailByPhoneNumber(phoneNumber);
        return ResponseEntity.ok(email);
    }

    @Operation(summary = "비밀번호 찾기", description = "사용자의 전화번호를 이용하여 임시 비밀번호를 발송")
    @PostMapping("/find-password")
    public ResponseEntity<String> findPassword(@RequestParam String phoneNumber) {
        String tempPassword = userService.sendTemporaryPassword(phoneNumber);
        return ResponseEntity.ok("Temporary password sent: " + tempPassword);
    }

    @Operation(summary = "비밀번호 재설정", description = "새 비밀번호로 사용자의 비밀번호를 재설정")
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

    @Operation(summary = "사용자 정보 및 게시물 조회", description = "사용자의 프로필 정보와 그 사용자가 작성한 게시물을 조회")
    @GetMapping("/{userId}/mypage")
    public ResponseEntity<User> getUserProfile(@PathVariable Long userId) {
        User user = userService.getUserProfile(userId);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "이메일 인증", description = "이메일 인증을 처리")
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
