package com.contest.grass.dto;

import java.util.List;

public class CartRequestDto {
    private Long userId;                        // 사용자 ID
    private Long itemId;                        // 상품 ID (아이템 추가/업데이트 시 사용)
    private Integer quantity;                   // 상품 수량 (아이템 추가/업데이트 시 사용)
    private Double price;                       // 상품 가격 (장바구니 아이템의 가격)
    private List<CartItemDto> cartItems;        // 장바구니 아이템 목록 (장바구니 조회 시 사용)
    private Double totalAmount;                 // 장바구니 총 금액 (장바구니 조회 시 사용)

    // 기본 생성자
    public CartRequestDto() {}

    // 모든 필드를 포함하는 생성자
    public CartRequestDto(Long userId, Long itemId, Integer quantity, Double price, List<CartItemDto> cartItems, Double totalAmount) {
        this.userId = userId;
        this.itemId = itemId;
        this.quantity = quantity;
        this.price = price;
        this.cartItems = cartItems;
        this.totalAmount = totalAmount;
    }

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<CartItemDto> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItemDto> cartItems) {
        this.cartItems = cartItems;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    // 내부 클래스 CartItemDto
    public static class CartItemDto {
        private Long itemId;        // 상품 ID
        private Integer quantity;   // 상품 수량
        private Double price;       // 상품 가격

        // 기본 생성자
        public CartItemDto() {}

        // 모든 필드를 포함하는 생성자
        public CartItemDto(Long itemId, Integer quantity, Double price) {
            this.itemId = itemId;
            this.quantity = quantity;
            this.price = price;
        }

        // Getters and Setters
        public Long getItemId() {
            return itemId;
        }

        public void setItemId(Long itemId) {
            this.itemId = itemId;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }
    }
}