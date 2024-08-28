package com.contest.grass.dto;

import java.util.List;

public class CartResponseDto {
    private Long userId;                        // 사용자 ID
    private List<CartItemDto> cartItems;        // 장바구니 아이템 목록
    private Double totalAmount;                 // 장바구니 총 금액

    // 기본 생성자
    public CartResponseDto() {}

    // 모든 필드를 포함하는 생성자
    public CartResponseDto(Long userId, List<CartItemDto> cartItems, Double totalAmount) {
        this.userId = userId;
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
        private String itemName;    // 상품명
        private Integer quantity;   // 상품 수량
        private Double price;       // 상품 가격
        private Double totalPrice;  // 해당 아이템의 총 가격 (수량 * 가격)

        // 기본 생성자
        public CartItemDto() {}

        // 모든 필드를 포함하는 생성자
        public CartItemDto(Long itemId, String itemName, Integer quantity, Double price, Double totalPrice) {
            this.itemId = itemId;
            this.itemName = itemName;
            this.quantity = quantity;
            this.price = price;
            this.totalPrice = totalPrice;
        }

        // Getters and Setters
        public Long getItemId() {
            return itemId;
        }

        public void setItemId(Long itemId) {
            this.itemId = itemId;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
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

        public Double getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(Double totalPrice) {
            this.totalPrice = totalPrice;
        }
    }
}