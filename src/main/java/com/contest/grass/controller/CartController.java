package com.contest.grass.controller;

import com.contest.grass.entity.Cart;
import com.contest.grass.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // 모든 카트 항목 조회 (GET)
    @GetMapping
    public List<Cart> getAllCartItems() {
        return cartService.getAllCartItems();
    }

    // 특정 카트 항목 조회 (GET)
    @GetMapping("/{cartId}")
    public ResponseEntity<Cart> getCartItem(@PathVariable Long cartId) {
        Cart cart = cartService.getCartItem(cartId);
        return ResponseEntity.ok(cart);
    }

    // 상품 개수 업데이트 (POST)
    @PostMapping("/{cartId}/count")
    public ResponseEntity<Cart> updateCartItemCount(@PathVariable Long cartId, @RequestParam Integer count) {
        Cart updatedCart = cartService.updateCartItemCount(cartId, count);
        return ResponseEntity.ok(updatedCart);
    }

    // 총 금액 업데이트 (GET & POST)
    @PostMapping("/{cartId}/totalPrice")
    public ResponseEntity<Cart> updateTotalPrice(@PathVariable Long cartId,
                                                 @RequestParam double itemPrice,
                                                 @RequestParam double shippingFee) {
        Cart updatedCart = cartService.updateTotalPrice(cartId, itemPrice, shippingFee);
        return ResponseEntity.ok(updatedCart);
    }

    // 구매하기 버튼 (총 금액 확인) (GET)
    @GetMapping("/{cartId}/checkout")
    public ResponseEntity<Double> checkout(@PathVariable Long cartId) {
        Cart cart = cartService.getCartItem(cartId);
        return ResponseEntity.ok(cart.getTotalPrice());
    }
}