package com.contest.grass.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemDetailRequestDto {
    // 공통 필드
    private String itemName;       // 상품명
    private String description;    // 상품 설명
    private String detailedDescription; // 상품 상세 설명
    private Integer stock;         // 재고

    // 등록 및 업데이트에 사용되는 필드
    private Double price;          // 상품 가격
    private String category;       // 상품 카테고리

    // 검색에 사용되는 필드
    private String searchKeyword;  // 검색어
    private Double minPrice;       // 가격 범위 (최소)
    private Double maxPrice;       // 가격 범위 (최대)
}
