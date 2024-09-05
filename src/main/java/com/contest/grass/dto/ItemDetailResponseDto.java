package com.contest.grass.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
}
