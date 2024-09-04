package com.contest.grass.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;

@Entity
@Schema(description = "Item entity representing a product available for sale")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the item", example = "21")
    private Long ItemId;

    @Column(length = 10)
    @Schema(description = "Title or name of the item", example = "Apple")
    private String title; // 상품명

    @Schema(description = "Price of the item in currency", example = "1000")
    private Integer price;

    @Schema(description = "Sale status or discount information", example = "10%")
    private String sale;

    @Schema(description = "Quantity of the item in stock", example = "50")
    private Integer qty; // 재고수량 quantity 줄임말

    // Getter and Setter methods
    public void setItemId(Long itemId) {
        ItemId = itemId;
    }

    public Long getItemId() {
        return ItemId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getPrice() {
        return price;
    }

    public void setSale(String sale) {
        this.sale = sale;
    }

    public String getSale() {
        return sale;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }
}
