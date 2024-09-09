package com.contest.grass.service;

import com.contest.grass.entity.Orders;
import com.contest.grass.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrdersService {

    private final OrdersRepository ordersRepository;

    @Autowired
    public OrdersService(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    // 주문 저장
    public Orders saveOrder(Orders orders) {
        return ordersRepository.save(orders);
    }

    // 특정 주문 조회
    public Orders findOrderById(Long ordersId) {
        return ordersRepository.findById(ordersId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + ordersId));
    }

    // 이름 업데이트
    public Orders updateName(Long ordersId, String name) {
        Orders order = findOrderById(ordersId);
        order.setName(name);
        return saveOrder(order);
    }

    // 전화번호 업데이트
    public Orders updatePhoneNumber(Long ordersId, String phoneNumber) {
        Orders order = findOrderById(ordersId);
        order.setPhoneNumber(phoneNumber);
        return saveOrder(order);
    }

    // 주소 업데이트
    public Orders updateAddress(Long ordersId, String address) {
        Orders order = findOrderById(ordersId);
        order.setAddress(address);
        return saveOrder(order);
    }

    // 상세주소 업데이트
    public Orders updateDetailAddress(Long ordersId, String detailAddress) {
        Orders order = findOrderById(ordersId);
        order.setDetailAddress(detailAddress);
        return saveOrder(order);
    }

    // 배송 요청사항 업데이트
    public Orders updateRequest(Long ordersId, String request) {
        Orders order = findOrderById(ordersId);
        order.setRequest(request);
        return saveOrder(order);
    }

    // 공동현관 출입번호 업데이트
    public Orders updateDoorPassword(Long ordersId, String doorpassword) {
        Orders order = findOrderById(ordersId);
        order.setDoorpassword(doorpassword);
        return saveOrder(order);
    }

    // 결제 처리
    public String processPayment(Long ordersId) {
        Orders order = findOrderById(ordersId);
        Orders.PaymentMethod paymentMethod = order.getPaymentMethod();

        switch (paymentMethod) {
            case NPay:
                return processNPay(order);
            case KakaoPay:
                return processKakaoPay(order);
            case CreditCard:
                return processCreditCard(order);
            default:
                throw new IllegalArgumentException("Invalid payment method: " + paymentMethod);
        }
    }

    private String processNPay(Orders order) {
        // NPay 결제 처리 로직
        return "NPay 결제가 성공적으로 처리되었습니다.";
    }

    private String processKakaoPay(Orders order) {
        // KakaoPay 결제 처리 로직
        return "KakaoPay 결제가 성공적으로 처리되었습니다.";
    }

    private String processCreditCard(Orders order) {
        // 신용카드 결제 처리 로직
        return "신용카드 결제가 성공적으로 처리되었습니다.";
    }
}