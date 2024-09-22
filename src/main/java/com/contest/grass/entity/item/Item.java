package com.contest.grass.entity.item;


import com.contest.grass.entity.Category;
import com.contest.grass.exception.NotEnoughStockException;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter @Setter
public class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String sale;
    private String name;
    private String price;
    private int qty;
    private String company;
    private String secondcategory;
    private String type;

    @ManyToMany(mappedBy = "items")
    @JsonBackReference
    private List<Category> categories = new ArrayList<>();

    public void addStock(int quantity) {
        this.qty += quantity;
    }

    public void removeStock(int quantity) {
        int restStock = this.qty - quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        this.qty = restStock;
    }
}