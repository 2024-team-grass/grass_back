package com.contest.grass.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Schema(description = "Category entity representing a hierarchical structure of product categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the category", example = "1")
    private Long CategoryId;

    @Schema(description = "Name of the category", example = "Electronics")
    private String name;

    // Parent-Child Relationship
    // Bidirectional (parent <-> child)
    // Parent
    @ManyToOne
    @JoinColumn(name = "PARENT_ID")
    @Schema(description = "Parent category, if any")
    private Category parent;

    // Child
    @OneToMany(mappedBy = "parent")
    @Schema(description = "List of child categories")
    private List<Category> child = new ArrayList<>();

    // Many-to-Many Relationship (Item <-> Category)
    @ManyToMany
    @JoinTable(name = "CATEGORY_ITEM",
            joinColumns = @JoinColumn(name = "CATEGORY_ID"),
            inverseJoinColumns = @JoinColumn(name = "ITEM_ID")
    )
    @Schema(description = "List of items associated with this category")
    private List<Item> items = new ArrayList<>();

    // Getters and Setters
    public Long getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(Long categoryId) {
        CategoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public List<Category> getChild() {
        return child;
    }

    public void setChild(List<Category> child) {
        this.child = child;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
