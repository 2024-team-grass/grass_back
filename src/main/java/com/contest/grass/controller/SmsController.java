package com.contest.grass.controller;

import com.contest.grass.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/sms")
public class SmsController {

    @Autowired
    private SmsService smsService;

    // 인증 코드 저장을 위한 임시 저장소 (실제 서비스에서는 Redis나 DB에 저장하는 것이 좋음)
    private Map<String, String> verificationCodes = new HashMap<>();
    private Map<String, LocalDateTime> codeTimestamps = new HashMap<>();

    // 전화번호로 인증 코드 전송
    public String sendVerificationCode(@RequestParam String phoneNumber) {
        String verificationCode = smsService.generateVerificationCode();
        smsService.sendVerificationCode(phoneNumber, verificationCode);
        verificationCodes.put(phoneNumber, verificationCode);
        codeTimestamps.put(phoneNumber, LocalDateTime.now());
        return "Verification code sent to " + phoneNumber;
    }

    // 인증 코드 검증
    public String verifyCode(@RequestParam String phoneNumber, @RequestParam String code) {
        String storedCode = verificationCodes.get(phoneNumber);
        LocalDateTime timeCreated = codeTimestamps.get(phoneNumber);

        if (storedCode == null || timeCreated == null) {
            return "Invalid or expired verification code.";
        }

        if (!smsService.isCodeValid(timeCreated)) {
            return "Verification code has expired.";
        }

        if (smsService.verifyCode(code, storedCode)) {
            // 인증 성공 시 추가 로직 처리 (예: 로그인, 회원가입 등)
            return "Verification successful!";
        } else {
            return "Invalid verification code.";
        }
    }
}