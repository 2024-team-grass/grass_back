package com.contest.grass.service;

import org.springframework.stereotype.Service;

@Service
public class EmailService {

    // 인증번호 이메일 전송
    public void sendVerificationCode(String email, String verificationCode) {
        String subject = "Your Email Verification Code";
        String message = "Your verification code is: " + verificationCode;
        sendSimpleMessage(email, subject, message);
    }

    // 간단한 이메일 발송 메소드
    private void sendSimpleMessage(String to, String subject, String text) {
        // 이메일 전송 로직 구현 (메일 전송 라이브러리 사용)
    }
}
