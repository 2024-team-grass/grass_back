package com.contest.grass.service;

import com.contest.grass.entity.*;
import com.contest.grass.entity.item.Item;
import com.contest.grass.repository.ItemRepository;
import com.contest.grass.repository.OrdersRepository;
import com.contest.grass.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.contest.grass.entity.Orders.PaymentMethod.NPay;

@Service
public class OrdersService {

    private final OrdersRepository ordersRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Autowired
    public OrdersService(OrdersRepository ordersRepository, UserRepository userRepository, ItemRepository itemRepository) {
        this.ordersRepository = ordersRepository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
    }

    public List<Orders> findOrdersByUserId(Long userId) {
        return ordersRepository.findByUserId(userId);
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
    public String processPayment(Orders orders) {
        Orders.PaymentMethod paymentMethod = orders.getPaymentMethod();

        switch (paymentMethod) {
            case NPay:
                return processNPay(orders);
            case KakaoPay:
                return processKakaoPay(orders);
            case CreditCard:
                return processCreditCard(orders);
            default:
                throw new IllegalArgumentException("Invalid payment method: " + paymentMethod);
        }
    }

    private String processNPay(Orders orders) {
        // NPay 결제 처리 로직
        return "NPay 결제가 성공적으로 처리되었습니다.";
    }

    private String processKakaoPay(Orders orders) {
        // KakaoPay 결제 처리 로직
        return "KakaoPay 결제가 성공적으로 처리되었습니다.";
    }

    private String processCreditCard(Orders orders) {
        // CreditCard 결제 처리 로직
        return "신용카드 결제가 성공적으로 처리되었습니다.";
    }

    @Transactional
    public Long order(Long userId, Long itemId, int count) {

        // 엔티티 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found with id: " + itemId));

        // 배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(user.getAddress());
        delivery.setStatus(DeliveryStatus.READY);

        // 주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, Integer.parseInt(item.getPrice()), count);

        // 주문 생성
        Orders orders = Orders.createOrder(user, delivery, orderItem);

        // 주문 저장
        ordersRepository.save(orders);

        return orders.getOrderId();
    }
}
