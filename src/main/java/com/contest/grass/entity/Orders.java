package com.contest.grass.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Schema(description = "Orders entity representing a customer's order in the system")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the order", example = "41")
    private Long OrderId;

    @Column(nullable = false, length = 10)
    @Schema(description = "Name of the customer placing the order", example = "John Doe")
    private String name;

    @Column(nullable = false, length = 11)
    @Schema(description = "Phone number of the customer", example = "01012345678")
    private Integer phoneNumber;

    @Column(nullable = false, length = 50)
    @Schema(description = "Delivery address for the order", example = "123 Main St, Seoul, Korea")
    private String address;

    @Column(length = 30)
    @Schema(description = "Detailed address information, such as apartment number", example = "Apt 101")
    private String detailAddress; // 상세주소

    @Column(length = 50)
    @Schema(description = "Special requests or instructions for the delivery", example = "Leave the package at the door")
    private String request; // 요청사항

    @Column(length = 30)
    @Schema(description = "Door password for access to shared building entrance", example = "1234")
    private String doorpassword; // 공동현관 출입번호

    public enum PaymentMethod {
        NPay,
        KakaoPay,
        CreditCard;
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Schema(description = "Payment method used for the order", example = "CreditCard")
    private PaymentMethod paymentMethod; // 결제 수단

    @Column(nullable = false)
    @Schema(description = "Total amount for the order in currency", example = "25000")
    private Integer totalAmount; // 주문 총액
}
