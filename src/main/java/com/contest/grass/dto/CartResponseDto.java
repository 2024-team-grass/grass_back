package com.contest.grass.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "장바구니 응답 DTO")
public class CartResponseDto {

    @Schema(description = "사용자 ID", example = "123", required = true)
    private Long userId;  // 사용자 ID

    @Schema(description = "장바구니 아이템 목록", required = true)
    private List<CartItemDto> cartItems;  // 장바구니 아이템 목록

    @Schema(description = "장바구니 총 금액", example = "50000.0", required = true)
    private Double totalAmount;  // 장바구니 총 금액

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "장바구니 아이템 DTO")
    public static class CartItemDto {

        @Schema(description = "상품 ID", example = "456", required = true)
        private Long itemId;  // 상품 ID

        @Schema(description = "상품명", example = "Apple", required = true)
        private String itemName;  // 상품명

        @Schema(description = "상품 수량", example = "2", required = true)
        private Integer quantity;  // 상품 수량

        @Schema(description = "상품 가격", example = "10000.0", required = true)
        private Double price;  // 상품 가격

        @Schema(description = "해당 아이템의 총 가격 (수량 * 가격)", example = "20000.0", required = true)
        private Double totalPrice;  // 해당 아이템의 총 가격
    }
}
