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
        // CreditCard 결제 처리 로직
        return "신용카드 결제가 성공적으로 처리되었습니다.";
    }
}