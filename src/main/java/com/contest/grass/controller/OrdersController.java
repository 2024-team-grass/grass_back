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

    @Operation(summary = "이름 업데이트", description = "주문의 이름을 업데이트")
    @PostMapping("/name")
    public ResponseEntity<Orders> updateName(@RequestBody Orders orders, @RequestParam String name) {
        orders.setName(name);
        Orders updatedOrder = ordersService.saveOrder(orders);
        return ResponseEntity.ok(updatedOrder);
    }

    @Operation(summary = "전화번호 업데이트", description = "주문의 전화번호를 업데이트")
    @PostMapping("/phoneNumber")
    public ResponseEntity<Orders> updatePhoneNumber(@RequestBody Orders orders, @RequestParam Integer phoneNumber) {
        orders.setPhoneNumber(phoneNumber);
        Orders updatedOrder = ordersService.saveOrder(orders);
        return ResponseEntity.ok(updatedOrder);
    }

    @Operation(summary = "주소 업데이트", description = "주문의 주소를 업데이트")
    @PostMapping("/address")
    public ResponseEntity<Orders> updateAddress(@RequestBody Orders orders, @RequestParam String address) {
        orders.setAddress(address);
        Orders updatedOrder = ordersService.saveOrder(orders);
        return ResponseEntity.ok(updatedOrder);
    }

    @Operation(summary = "상세주소 업데이트", description = "주문의 상세주소를 업데이트")
    @PostMapping("/detailAddress")
    public ResponseEntity<Orders> updateDetailAddress(@RequestBody Orders orders, @RequestParam String detailAddress) {
        orders.setDetailAddress(detailAddress);
        Orders updatedOrder = ordersService.saveOrder(orders);
        return ResponseEntity.ok(updatedOrder);
    }

    @Operation(summary = "배송 요청사항 업데이트", description = "주문에 대한 배송 요청사항을 업데이트")
    @PostMapping("/request")
    public ResponseEntity<Orders> updateRequest(@RequestBody Orders orders, @RequestParam String request) {
        orders.setRequest(request);
        Orders updatedOrder = ordersService.saveOrder(orders);
        return ResponseEntity.ok(updatedOrder);
    }

    @Operation(summary = "공동현관 출입번호 업데이트", description = "주문에 대한 공동현관 출입번호를 업데이트")
    @PostMapping("/doorpassword")
    public ResponseEntity<Orders> updateDoorPassword(@RequestBody Orders orders, @RequestParam String doorpassword) {
        orders.setDoorpassword(doorpassword);
        Orders updatedOrder = ordersService.saveOrder(orders);
        return ResponseEntity.ok(updatedOrder);
    }

    @Operation(summary = "결제 처리", description = "주문에 대한 결제를 처리")
    @PostMapping("/payment")
    public ResponseEntity<String> processPayment(@RequestBody Orders orders) {
        String result = ordersService.processPayment(orders);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "총액 확인", description = "주문 ID로 주문의 총액을 확인")
    @GetMapping("/totalAmount/{ordersId}")
    public ResponseEntity<Integer> getTotalAmount(@PathVariable Long ordersId) {
        Orders orders = ordersService.findOrderById(ordersId);
        return ResponseEntity.ok(orders.getTotalAmount());
    }
}
