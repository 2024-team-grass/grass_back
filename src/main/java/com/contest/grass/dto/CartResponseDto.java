package com.contest.grass.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartResponseDto {
    private Long userId;                        // 사용자 ID
    private List<CartItemDto> cartItems;        // 장바구니 아이템 목록
    private Double totalAmount;                 // 장바구니 총 금액

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CartItemDto {
        private Long itemId;        // 상품 ID
        private String itemName;    // 상품명
        private Integer quantity;   // 상품 수량
        private Double price;       // 상품 가격
        private Double totalPrice;  // 해당 아이템의 총 가격 (수량 * 가격)
    }
}
