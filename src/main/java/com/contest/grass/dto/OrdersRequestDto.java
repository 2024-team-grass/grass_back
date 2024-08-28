package com.contest.grass.dto;

public class OrdersRequestDto {
    private String name;
    private Integer phoneNumber;
    private String address;
    private String detailAddress; // 상세주소
    private String request; // 요청사항
    private String doorpassword; // 공동현관 출입번호
    private String paymentMethod; // 결제 수단 (NaverPay, KakaoPay, CreditCard 등)
    private Integer totalAmount; // 주문 총액

    // 기본 생성자
    public OrdersRequestDto() {}

    // 모든 필드를 포함하는 생성자
    public OrdersRequestDto(String name, Integer phoneNumber, String address, String detailAddress, String request, String doorpassword, String paymentMethod, Integer totalAmount) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.detailAddress = detailAddress;
        this.request = request;
        this.doorpassword = doorpassword;
        this.paymentMethod = paymentMethod;
        this.totalAmount = totalAmount;
    }

    // Getters and Setters
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