package com.contest.grass.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;

@Entity
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long OrderId;

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

    @Column(nullable = false, length = 20)
    private String paymentMethod; // 결제 수단 (NaverPay, KakaoPay, CreditCard 등)

    @Column(nullable = false)
    private Integer totalAmount; // 주문 총액

    // Getter와 Setter 메서드
    public Long getOrderId() {
        return OrderId;
    }

    public void setOrderId(Long orderId) {
        OrderId = orderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getDoorpassword() {
        return doorpassword;
    }

    public void setDoorpassword(String doorpassword) {
        this.doorpassword = doorpassword;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }
}