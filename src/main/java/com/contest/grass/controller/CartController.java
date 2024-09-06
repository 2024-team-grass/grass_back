package com.contest.grass.controller;

import com.contest.grass.entity.Cart;
import com.contest.grass.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@Tag(name = "Cart", description = "Operations related to shopping cart management") // Swagger 태그 추가
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @Operation(summary = "모든 카트 항목 조회", description = "모든 카트 항목을 조회")
    @GetMapping
    public List<Cart> getAllCartItems() {
        return cartService.getAllCartItems();
    }

    @Operation(summary = "특정 카트 항목 조회", description = "특정 ID를 가진 카트 항목을 조회")
    @GetMapping("/{cartId}")
    public ResponseEntity<Cart> getCartItem(@PathVariable Long cartId) {
        Cart cart = cartService.getCartItem(cartId);
        return ResponseEntity.ok(cart);
    }

    @Operation(summary = "상품 개수 업데이트", description = "특정 카트 항목의 상품 개수를 업데이트")
    @PostMapping("/{cartId}/count")
    public ResponseEntity<Cart> updateCartItemCount(@PathVariable Long cartId, @RequestParam Integer count) {
        Cart updatedCart = cartService.updateCartItemCount(cartId, count);
        return ResponseEntity.ok(updatedCart);
    }

    @Operation(summary = "총 금액 업데이트", description = "특정 카트 항목의 총 금액을 업데이트")
    @PostMapping("/{cartId}/totalPrice")
    public ResponseEntity<Cart> updateTotalPrice(@PathVariable Long cartId,
                                                 @RequestParam double itemPrice,
                                                 @RequestParam double shippingFee) {
        Cart updatedCart = cartService.updateTotalPrice(cartId, itemPrice, shippingFee);
        return ResponseEntity.ok(updatedCart);
    }

    @Operation(summary = "구매하기", description = "특정 카트 항목의 총 금액을 확인하고 구매")
    @GetMapping("/{cartId}/checkout")
    public ResponseEntity<Double> checkout(@PathVariable Long cartId) {
        Cart cart = cartService.getCartItem(cartId);
        return ResponseEntity.ok(cart.getTotalPrice());
    }
}
