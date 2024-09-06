package com.contest.grass.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    private String name;

    // Parent-Child Relationship
    // Bidirectional (parent <-> child)
    // Parent
    @ManyToOne
    @JoinColumn(name = "PARENT_ID")
    private Category parent;

    // Child
    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    // Many-to-Many Relationship (Item <-> Category)
    @ManyToMany
    @JoinTable(name = "CATEGORY_ITEM",
            joinColumns = @JoinColumn(name = "CATEGORY_ID"),
            inverseJoinColumns = @JoinColumn(name = "ITEM_ID")
    )
    private List<Item> items = new ArrayList<>();
}
