package com.contest.grass.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @JoinColumn(nullable = false)
    private String name;

    @Column(length = 11)
    private String phoneNumber;

    @Column(length = 50)
    private String address;

    @Column(length = 30)
    private String detailAddress; // 상세주소

    @Column(length = 50)
    private String request; // 요청사항

    @Column(length = 30)
    private String doorpassword; // 공동현관 출입번호

    @Enumerated(EnumType.STRING)
    private Orderrefund orderrefund;

    private String reason;

    public enum PaymentMethod {
        NPay,
        KakaoPay,
        CreditCard;
    }

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod; // 결제 수단

    private Integer totalAmount; // 주문 총액

    private LocalDateTime orderDate; //주문시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문상태 [ORDER, CANCEL]

    //상품 환불 내용
    private String content;

    @JsonIgnore
    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    public void setMember(User user) {
        this.user = user;
        user.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrders(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrders(this);
    }

    public static Orders createOrder(User user, Delivery delivery, OrderItem... orderItems) {
        Orders orders = new Orders();
        orders.setMember(user);
        orders.setDelivery(delivery);
        for (OrderItem orderItem : orderItems) {
            orders.addOrderItem(orderItem);
        }
        orders.setStatus(OrderStatus.ORDER);
        orders.setOrderDate(LocalDateTime.now());
        return orders;
    }

    public void cancel() {
        if (delivery.getStatus() == DeliveryStatus.NOT) {
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }

        this.setStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    public int getTotalPrice() {
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }
}
