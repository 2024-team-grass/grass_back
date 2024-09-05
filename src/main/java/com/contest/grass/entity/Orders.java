package com.contest.grass.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Column(nullable = false, length = 10)
    private String name;

    @Column(nullable = false, length = 11)
    private Integer phoneNumber;

    @Column(nullable = false, length = 50)
    private String address;

    @Column(length = 30)
    private String detailAddress; // 상세주소

    @Column(length = 50)
    private String request; // 요청사항

    @Column(length = 30)
    private String doorpassword; // 공동현관 출입번호

    public enum PaymentMethod {
        NPay,
        KakaoPay,
        CreditCard;
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod paymentMethod; // 결제 수단

    @Column(nullable = false)
    private Integer totalAmount; // 주문 총액
}
