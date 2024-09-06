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
@Tag(name = "Cart", description = "Operations related to shopping cart management")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // 모든 카트 항목 조회
    @Operation(summary = "모든 카트 항목 조회", description = "모든 카트 항목을 조회")
    @GetMapping
    public List<Cart> getAllCartItems() {
        return cartService.getAllCartItems();
    }

    // 특정 카트 항목 조회
    @Operation(summary = "특정 카트 항목 조회", description = "특정 ID를 가진 카트 항목을 조회")
    @GetMapping("/{cartId}")
    public ResponseEntity<Cart> getCartItem(@PathVariable Long cartId) {
        Cart cart = cartService.getCartItem(cartId);
        return ResponseEntity.ok(cart);
    }

    // 상품 개수 업데이트
    @Operation(summary = "상품 개수 업데이트", description = "특정 카트 항목의 상품 개수를 업데이트")
    @PostMapping("/{cartId}/count")
    public ResponseEntity<Cart> updateCartItemCount(@PathVariable Long cartId, @RequestParam Integer count) {
        Cart updatedCart = cartService.updateCartItemCount(cartId, count);
        return ResponseEntity.ok(updatedCart);
    }



    // 총 금액 업데이트
    @Operation(summary = "총 금액 업데이트", description = "특정 카트 항목의 총 금액을 업데이트")
    @PostMapping("/{cartId}/totalPrice")
    public ResponseEntity<Cart> updateTotalPrice(@PathVariable Long cartId,
                                                 @RequestParam double itemPrice,
                                                 @RequestParam double shippingFee) {
        Cart updatedCart = cartService.updateTotalPrice(cartId, itemPrice, shippingFee);
        return ResponseEntity.ok(updatedCart);
    }

    // 구매하기
    @Operation(summary = "구매하기", description = "특정 카트 항목의 총 금액을 확인하고 구매를 진행")
    @GetMapping("/{cartId}/checkout")
    public ResponseEntity<String> checkout(@PathVariable Long cartId) {
        // 장바구니 항목을 가져옴
        Cart cart = cartService.getCartItem(cartId);

        // 총 금액 확인
        double totalPrice = cart.getTotalPrice();

        // 구매 절차가 여기서 진행됨 (예: 결제 시스템과 연동 등)
        boolean purchaseSuccessful = cartService.processPurchase(cart); // 구매 처리 로직

        // 구매 성공 여부에 따른 응답
        if (purchaseSuccessful) {
            return ResponseEntity.ok("구매가 성공적으로 완료되었습니다. 총 금액: " + totalPrice + "원");
        } else {
            return ResponseEntity.badRequest().body("구매 실패. 다시 시도해 주세요.");
        }
    }
}