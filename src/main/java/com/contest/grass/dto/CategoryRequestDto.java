package com.contest.grass.dto;

import java.util.List;

public class CategoryRequestDto {
    private Long categoryId;         // 카테고리 ID (업데이트 시 필요)
    private String name;             // 카테고리 이름
    private Long parentId;           // 부모 카테고리 ID (최상위 카테고리인 경우 null)
    private List<CategoryRequestDto> childCategories; // 자식 카테고리 목록

    // 기본 생성자
    public CategoryRequestDto() {}

    // 모든 필드를 포함하는 생성자
    public CategoryRequestDto(Long categoryId, String name, Long parentId, List<CategoryRequestDto> childCategories) {
        this.categoryId = categoryId;
        this.name = name;
        this.parentId = parentId;
        this.childCategories = childCategories;
    }

    // Getters and Setters
    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public List<CategoryRequestDto> getChildCategories() {
        return childCategories;
    }

    public void setChildCategories(List<CategoryRequestDto> childCategories) {
        this.childCategories = childCategories;
    }
}