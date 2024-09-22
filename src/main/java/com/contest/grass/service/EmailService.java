package com.contest.grass.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    // 인증번호 이메일 전송
    public void sendVerificationCode(String email, String verificationCode) {
        String subject = "Your Email Verification Code";
        String message = "Your verification code is: " + verificationCode;
        sendSimpleMessage(email, subject, message);
    }

    // 간단한 이메일 발송 메소드
    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
}
