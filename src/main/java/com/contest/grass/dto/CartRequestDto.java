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
public class CartRequestDto {
    private Long userId;                        // 사용자 ID
    private Long itemId;                        // 상품 ID (아이템 추가/업데이트 시 사용)
    private Integer quantity;                   // 상품 수량 (아이템 추가/업데이트 시 사용)
    private Double price;                       // 상품 가격 (장바구니 아이템의 가격)
    private List<CartItemDto> cartItems;        // 장바구니 아이템 목록 (장바구니 조회 시 사용)
    private Double totalAmount;                 // 장바구니 총 금액 (장바구니 조회 시 사용)

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CartItemDto {
        private Long itemId;        // 상품 ID
        private Integer quantity;   // 상품 수량
        private Double price;       // 상품 가격
    }
}
