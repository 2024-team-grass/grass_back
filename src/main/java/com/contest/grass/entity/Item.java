package com.contest.grass.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ItemId;

    @Column(length = 10)
    private String title; //상품명

    private Integer price;
    private String sale;
    private Integer qty; //재고수량 quantity 줄임말





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