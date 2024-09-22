package com.contest.grass.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

@Service
public class SmsService {

    @Value("${twilio.account.sid}")
    private String ACCOUNT_SID;

    @Value("${twilio.auth.token}")
    private String AUTH_TOKEN;

    @Value("${twilio.phone.number}")
    private String FROM_NUMBER;

    // Twilio 초기화 메서드
    @PostConstruct
    public void initTwilio() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    // 인증 코드 전송 로직
    public void sendVerificationCode(String toPhoneNumber, String verificationCode) {
        Message.creator(
                        new PhoneNumber(toPhoneNumber),
                        new PhoneNumber(FROM_NUMBER),
                        "Your verification code is: " + verificationCode)
                .create();
    }

    // 코드 검증 로직
    public boolean verifyCode(String inputCode, String generatedCode) {
        return inputCode.equals(generatedCode);
    }

    // 만료 시간 관련 로직
    public boolean isCodeValid(LocalDateTime timeCreated) {
        LocalDateTime currentTime = LocalDateTime.now();
        return !timeCreated.plusMinutes(5).isBefore(currentTime);
    }

    // 인증 코드 생성 로직
    public String generateVerificationCode() {
        int randomPin = (int)(Math.random() * 9000) + 1000; // 4자리 숫자 생성
        return String.valueOf(randomPin);
    }
}