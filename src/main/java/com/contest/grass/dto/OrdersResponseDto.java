package com.contest.grass.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrdersResponseDto {
    private Long orderId;
    private String name;
    private Integer phoneNumber;
    private String address;
    private String detailAddress; // 상세주소
    private String request; // 요청사항
    private String doorpassword; // 공동현관 출입번호
    private String paymentMethod; // 결제 수단 (NaverPay, KakaoPay, CreditCard 등)
    private Integer totalAmount; // 주문 총액
    private String status; // 주문 상태 (예: 주문 접수, 배송 중, 배송 완료 등)
    private String message; // 응답 메시지 (예: 주문이 성공적으로 접수되었습니다.)
}
