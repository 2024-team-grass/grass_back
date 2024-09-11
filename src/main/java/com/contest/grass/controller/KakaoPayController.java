package com.contest.grass.controller;

import com.contest.grass.service.KakaoPayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/kakaopay")
public class KakaoPayController {

    private final KakaoPayService kakaoPayService;
    private static final Logger logger = LoggerFactory.getLogger(KakaoPayController.class);

    public KakaoPayController(KakaoPayService kakaoPayService) {
        this.kakaoPayService = kakaoPayService;
    }

    // 결제 준비
    @GetMapping("/ready")
    public ResponseEntity<String> kakaoPayReady() {
        String result = kakaoPayService.kakaoPayReady();
        return ResponseEntity.ok(result);
    }

    // 결제 성공
    @GetMapping("/success")
    public ResponseEntity<String> kakaoPaySuccess(@RequestParam("pg_token") String pgToken) {
        String result = kakaoPayService.kakaoPayApprove(pgToken);

        // pg_token을 콘솔에 출력
        logger.info("pg_token: " + pgToken);

        return ResponseEntity.ok(result);
    }

    // 결제 취소
    @GetMapping("/cancel")
    public ResponseEntity<String> kakaoPayCancel() {
        return ResponseEntity.ok("결제가 취소되었습니다.");
    }

    // 결제 실패
    @GetMapping("/fail")
    public ResponseEntity<String> kakaoPayFail() {
        return ResponseEntity.ok("결제가 실패하였습니다.");
    }
}