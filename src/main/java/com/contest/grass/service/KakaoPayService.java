package com.contest.grass.service;

import com.contest.grass.dto.KakaoPayReadyResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class KakaoPayService {

    private static final String KAKAO_PAY_READY_URL = "https://kapi.kakao.com/v1/payment/ready";
    private static final String KAKAO_PAY_APPROVE_URL = "https://kapi.kakao.com/v1/payment/approve";
    private static final String ADMIN_KEY = "074d4065b3ab748a45003e1e58874a92";  // Admin Key를 넣으세요
    private static final Logger logger = LoggerFactory.getLogger(KakaoPayService.class);
    private String tid; // 결제 고유 번호를 저장하는 변수

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;  // ObjectMapper 인스턴스 추가

    public KakaoPayService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;  // 생성자에서 ObjectMapper 주입
    }

    // 결제 준비 단계
    public String kakaoPayReady() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + ADMIN_KEY);
        headers.set("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("cid", "TC0ONETIME");
        params.add("partner_order_id", "1001");
        params.add("partner_user_id", "user_1234");
        params.add("item_name", "상품명");
        params.add("quantity", "1");
        params.add("total_amount", "1000");
        params.add("tax_free_amount", "0");
        params.add("approval_url", "http://localhost:8080/kakaopay/success");
        params.add("cancel_url", "http://localhost:8080/kakaopay/cancel");
        params.add("fail_url", "http://localhost:8080/kakaopay/fail");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        // 카카오페이 결제 준비 요청
        ResponseEntity<String> response = restTemplate.exchange(
                KAKAO_PAY_READY_URL,
                HttpMethod.POST,
                request,
                String.class
        );

        // JSON 응답을 KakaoPayReadyResponse 객체로 변환
        KakaoPayReadyResponse readyResponse = null;
        try {
            readyResponse = objectMapper.readValue(response.getBody(), KakaoPayReadyResponse.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();  // 예외 발생 시 스택 트레이스 출력
            return null;  // 예외가 발생하면 null 반환
        }

        // 결제 고유 번호 (tid) 저장
        tid = readyResponse.getTid();

        // 사용자에게 리다이렉트할 결제 페이지 URL 반환
        return readyResponse != null ? readyResponse.getNext_redirect_pc_url() : null;
    }

    // 결제 승인 단계
    public String kakaoPayApprove(String pgToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + ADMIN_KEY);
        headers.set("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("cid", "TC0ONETIME");
        params.add("tid", tid);  // 저장된 결제 고유 번호 사용
        params.add("partner_order_id", "1001");
        params.add("partner_user_id", "user_1234");
        params.add("pg_token", pgToken);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        // 카카오페이 결제 승인 요청
        ResponseEntity<String> response = restTemplate.exchange(
                KAKAO_PAY_APPROVE_URL,
                HttpMethod.POST,
                request,
                String.class
        );

        return response.getBody();  // 실제 응답 처리
    }
}