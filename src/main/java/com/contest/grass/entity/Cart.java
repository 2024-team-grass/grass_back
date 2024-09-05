package com.contest.grass.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Schema(description = "Cart entity representing an item added to the user's shopping cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the cart entry", example = "51")
    private Long CartId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @Schema(description = "User who owns the cart")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    @Schema(description = "Item added to the cart")
    private Item item;

    @Schema(description = "Quantity of the item selected by the user", example = "2")
    private Integer count; // 선택 개수

    @Transient
    @Schema(description = "Price of the individual item", example = "5000")
    private double itemPrice; // 개별 상품 금액

    @Transient
    @Schema(description = "Total price for the selected quantity of the item", example = "10000")
    private double totalPrice; // 총 금액

    @Schema(description = "Shipping fee for the items in the cart", example = "2500")
    private double shippingFee; // 배송비
}
