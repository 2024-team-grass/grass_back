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
    public Orders saveOrder(Orders order) {
        return ordersRepository.save(order);
    }

    // 특정 주문 조회
    public Orders findOrderById(Long orderId) {
        return ordersRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
    }

    // 결제 처리
    public String processPayment(Orders order) {
        String paymentMethod = order.getPaymentMethod();
        String paymentStatus;

        switch (paymentMethod) {
            case "NPay":
                paymentStatus = processNaverPay(order);
                break;
            case "KakaoPay":
                paymentStatus = processKakaoPay(order);
                break;
            case "CreditCard":
                paymentStatus = processCreditCard(order);
                break;
            default:
                throw new IllegalArgumentException("Invalid payment method: " + paymentMethod);
        }

        if ("Success".equals(paymentStatus)) {
            ordersRepository.save(order);
            return "Payment successful and order saved.";
        } else {
            return "Payment failed.";
        }
    }

    // 네이버페이 결제 처리
    private String processNaverPay(Orders order) {
        // 네이버페이 API 호출 로직
        // ...
        return "Success"; // 실패 시 "Failed"를 반환
    }

    // 카카오페이 결제 처리
    private String processKakaoPay(Orders order) {
        // 카카오페이 API 호출 로직
        // ...
        return "Success"; // 실패 시 "Failed"를 반환
    }

    // 신용카드 결제 처리
    private String processCreditCard(Orders order) {
        // 신용카드 결제 API 호출 로직
        // ...
        return "Success"; // 실패 시 "Failed"를 반환
    }
}