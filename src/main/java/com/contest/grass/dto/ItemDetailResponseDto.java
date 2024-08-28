package com.contest.grass.dto;

public class ItemDetailResponseDto {
    // 공통 필드
    private Long itemId;            // 상품의 고유 ID
    private String itemName;        // 상품명
    private String description;     // 상품 설명
    private String detailedDescription; // 상품 상세 설명
    private Integer stock;          // 재고

    // 등록 및 업데이트에 사용되는 필드
    private Double price;           // 상품 가격
    private String category;        // 상품 카테고리

    // 검색 결과 관련 필드
    private String searchKeyword;   // 검색어 (필요에 따라 포함)
    private Double minPrice;        // 검색된 가격 범위 (최소)
    private Double maxPrice;        // 검색된 가격 범위 (최대)

    // 기본 생성자
    public ItemDetailResponseDto() {}

    // 모든 필드를 포함하는 생성자
    public ItemDetailResponseDto(Long itemId, String itemName, String description, String detailedDescription, Integer stock, Double price, String category, String searchKeyword, Double minPrice, Double maxPrice) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.description = description;
        this.detailedDescription = detailedDescription;
        this.stock = stock;
        this.price = price;
        this.category = category;
        this.searchKeyword = searchKeyword;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDetailedDescription() {
        return detailedDescription;
    }

    public void setDetailedDescription(String detailedDescription) {
        this.detailedDescription = detailedDescription;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSearchKeyword() {
        return searchKeyword;
    }

    public void setSearchKeyword(String searchKeyword) {
        this.searchKeyword = searchKeyword;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }
}