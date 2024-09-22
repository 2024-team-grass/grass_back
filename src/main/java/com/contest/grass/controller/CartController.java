package com.contest.grass.controller;

import com.contest.grass.dto.CartResponseDto;
import com.contest.grass.entity.Cart;
import com.contest.grass.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cart")
@Tag(name = "Cart", description = "장바구니 관리 API") // Swagger 태그 추가
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @Operation(summary = "모든 카트 항목 조회", description = "장바구니에 담긴 모든 항목을 조회합니다.")
    @GetMapping
    public List<Cart> getAllCartItems() {
        return cartService.getAllCartItems();
    }

    @Operation(summary = "특정 카트 항목 조회", description = "카트 ID로 특정 장바구니 항목을 조회합니다.")
    @GetMapping("/{cartId}")
    public ResponseEntity<Cart> getCartItem(@PathVariable Long cartId) {
        Cart cart = cartService.getCartItem(cartId);
        return ResponseEntity.ok(cart);
    }



    @Operation(summary = "상품 개수 업데이트", description = "카트 항목의 상품 개수를 업데이트합니다.")
    @PostMapping("/{cartId}/count")
    public ResponseEntity<Cart> updateCartItemCount(@PathVariable Long cartId, @RequestParam Integer count) {
        Cart updatedCart = cartService.updateCartItemCount(cartId, count);
        return ResponseEntity.ok(updatedCart);
    }

    @Operation(summary = "총 금액 업데이트", description = "카트 항목의 상품 가격과 배송비를 이용해 총 금액을 업데이트합니다.")
    @PostMapping("/{cartId}/totalPrice")
    public ResponseEntity<Cart> updateTotalPrice(@PathVariable Long cartId,
                                                 @RequestParam double itemPrice,
                                                 @RequestParam double shippingFee) {
        Cart updatedCart = cartService.updateTotalPrice(cartId, itemPrice, shippingFee);
        return ResponseEntity.ok(updatedCart);
    }

    @Operation(summary = "구매하기", description = "특정 카트 항목의 총 금액을 확인하고 구매합니다.")
    @GetMapping("/{cartId}/checkout")
    public ResponseEntity<Double> checkout(@PathVariable Long cartId) {
        Cart cart = cartService.getCartItem(cartId);
        return ResponseEntity.ok(cart.getTotalPrice());
    }


    @Operation(summary = "사용자 ID로 장바구니 항목 조회", description = "사용자 ID를 기반으로 장바구니에 담긴 항목을 조회합니다.")
    @GetMapping("/user/{userId}")
    public ResponseEntity<CartResponseDto> getCartItemsByUserId(@PathVariable Long userId) {
        System.out.println("Received request to get cart items for user ID: " + userId);
        List<Cart> carts = cartService.getCartItemsByUserId(userId);

        // 카트 항목 데이터 확인
        System.out.println("Fetched cart items: " + carts);

        // Cart 엔티티를 DTO로 변환
        List<CartResponseDto.CartItemDto> cartItemDtos = carts.stream().map(cart -> {
            CartResponseDto.CartItemDto dto = new CartResponseDto.CartItemDto();
            dto.setItemId(cart.getItem().getId());  // Item ID
            dto.setItemName(cart.getItem().getName());  // Item Name
            dto.setQuantity(cart.getCount());  // Quantity
            dto.setPrice(cart.getItemPrice());  // Price
            dto.setTotalPrice(cart.getTotalPrice());  // Total Price
            return dto;
        }).collect(Collectors.toList());

        // 총 금액 계산
        double totalAmount = carts.stream().mapToDouble(Cart::getTotalPrice).sum();

        // Response DTO 생성
        CartResponseDto responseDto = new CartResponseDto(userId, cartItemDtos, totalAmount);
        System.out.println("Response DTO: " + responseDto);

        return ResponseEntity.ok(responseDto);
    }

}
