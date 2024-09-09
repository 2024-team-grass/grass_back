package com.contest.grass.controller;

import com.contest.grass.entity.Orders;
import com.contest.grass.service.OrdersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@Tag(name = "Orders", description = "주문 관리 API") // Swagger 태그 추가
public class OrdersController {

    private final OrdersService ordersService;

    @Autowired
    public OrdersController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @Operation(summary = "이름 업데이트", description = "특정 주문의 이름을 업데이트")
    @PostMapping("/{ordersId}/name")
    public ResponseEntity<Orders> updateName(@PathVariable Long ordersId, @RequestParam String name) {
        Orders orders = ordersService.findOrderById(ordersId);
        orders.setName(name);
        Orders updatedOrder = ordersService.saveOrder(orders);
        return ResponseEntity.ok(updatedOrder);
    }

    @Operation(summary = "전화번호 업데이트", description = "특정 주문의 전화번호를 업데이트")
    @PostMapping("/{ordersId}/phoneNumber")
    public ResponseEntity<Orders> updatePhoneNumber(@PathVariable Long ordersId, @RequestParam String phoneNumber) {  // 전화번호 String으로 변경
        Orders orders = ordersService.findOrderById(ordersId);
        orders.setPhoneNumber(phoneNumber);
        Orders updatedOrder = ordersService.saveOrder(orders);
        return ResponseEntity.ok(updatedOrder);
    }

    @Operation(summary = "주소 업데이트", description = "특정 주문의 주소를 업데이트")
    @PostMapping("/{ordersId}/address")
    public ResponseEntity<Orders> updateAddress(@PathVariable Long ordersId, @RequestParam String address) {
        Orders orders = ordersService.findOrderById(ordersId);
        orders.setAddress(address);
        Orders updatedOrder = ordersService.saveOrder(orders);
        return ResponseEntity.ok(updatedOrder);
    }

    @Operation(summary = "상세주소 업데이트", description = "특정 주문의 상세주소를 업데이트")
    @PostMapping("/{ordersId}/detailAddress")
    public ResponseEntity<Orders> updateDetailAddress(@PathVariable Long ordersId, @RequestParam String detailAddress) {
        Orders orders = ordersService.findOrderById(ordersId);
        orders.setDetailAddress(detailAddress);
        Orders updatedOrder = ordersService.saveOrder(orders);
        return ResponseEntity.ok(updatedOrder);
    }

    @Operation(summary = "배송 요청사항 업데이트", description = "특정 주문의 배송 요청사항을 업데이트")
    @PostMapping("/{ordersId}/request")
    public ResponseEntity<Orders> updateRequest(@PathVariable Long ordersId, @RequestParam String request) {
        Orders orders = ordersService.findOrderById(ordersId);
        orders.setRequest(request);
        Orders updatedOrder = ordersService.saveOrder(orders);
        return ResponseEntity.ok(updatedOrder);
    }

    @Operation(summary = "공동현관 출입번호 업데이트", description = "특정 주문의 공동현관 출입번호를 업데이트")
    @PostMapping("/{ordersId}/doorpassword")
    public ResponseEntity<Orders> updateDoorPassword(@PathVariable Long ordersId, @RequestParam String doorpassword) {
        Orders orders = ordersService.findOrderById(ordersId);
        orders.setDoorpassword(doorpassword);
        Orders updatedOrder = ordersService.saveOrder(orders);
        return ResponseEntity.ok(updatedOrder);
    }

    @Operation(summary = "결제 처리", description = "특정 주문에 대한 결제를 처리")
    @PostMapping("/{ordersId}/payment")
    public ResponseEntity<String> processPayment(@PathVariable Long ordersId) {
        String result = ordersService.processPayment(ordersId);  // 주문 ID로 결제 처리
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "총액 확인", description = "주문 ID로 주문의 총액을 확인")
    @GetMapping("/totalAmount/{ordersId}")
    public ResponseEntity<Integer> getTotalAmount(@PathVariable Long ordersId) {
        Orders orders = ordersService.findOrderById(ordersId);
        return ResponseEntity.ok(orders.getTotalAmount());
    }
}