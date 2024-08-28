package com.contest.grass.controller;

import com.contest.grass.entity.Orders;
import com.contest.grass.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orderss")
public class OrdersController {

    private final OrdersService orderssService;

    @Autowired
    public OrdersController(OrdersService orderssService) {
        this.orderssService = orderssService;
    }

    // 이름 업데이트 (POST)
    @PostMapping("/name")
    public ResponseEntity<Orders> updateName(@RequestBody Orders orders, @RequestParam String name) {
        orders.setName(name);
        Orders updatedOrder = orderssService.saveOrder(orders);
        return ResponseEntity.ok(updatedOrder);
    }

    // 전화번호 업데이트 (POST)
    @PostMapping("/phoneNumber")
    public ResponseEntity<Orders> updatePhoneNumber(@RequestBody Orders orders, @RequestParam Integer phoneNumber) {
        orders.setPhoneNumber(phoneNumber);
        Orders updatedOrder = orderssService.saveOrder(orders);
        return ResponseEntity.ok(updatedOrder);
    }

    // 주소 업데이트 (POST)
    @PostMapping("/address")
    public ResponseEntity<Orders> updateAddress(@RequestBody Orders orders, @RequestParam String address) {
        orders.setAddress(address);
        Orders updatedOrder = orderssService.saveOrder(orders);
        return ResponseEntity.ok(updatedOrder);
    }

    // 상세주소 업데이트 (POST)
    @PostMapping("/detailAddress")
    public ResponseEntity<Orders> updateDetailAddress(@RequestBody Orders orders, @RequestParam String detailAddress) {
        orders.setDetailAddress(detailAddress);
        Orders updatedOrder = orderssService.saveOrder(orders);
        return ResponseEntity.ok(updatedOrder);
    }

    // 배송 요청사항 업데이트 (POST)
    @PostMapping("/request")
    public ResponseEntity<Orders> updateRequest(@RequestBody Orders orders, @RequestParam String request) {
        orders.setRequest(request);
        Orders updatedOrder = orderssService.saveOrder(orders);
        return ResponseEntity.ok(updatedOrder);
    }

    // 공동현관 출입번호 업데이트 (POST)
    @PostMapping("/doorpassword")
    public ResponseEntity<Orders> updateDoorPassword(@RequestBody Orders orders, @RequestParam String doorpassword) {
        orders.setDoorpassword(doorpassword);
        Orders updatedOrder = orderssService.saveOrder(orders);
        return ResponseEntity.ok(updatedOrder);
    }

    // 결제 처리 (POST)
    @PostMapping("/payment")
    public ResponseEntity<String> processPayment(@RequestBody Orders orders) {
        String result = orderssService.processPayment(orders);
        return ResponseEntity.ok(result);
    }

    // 구매하기 버튼 눌렀을 때 총액 확인 (GET)
    @GetMapping("/totalAmount/{ordersId}")
    public ResponseEntity<Integer> getTotalAmount(@PathVariable Long ordersId) {
        Orders orders = orderssService.findOrderById(ordersId);
        return ResponseEntity.ok(orders.getTotalAmount());
    }
}