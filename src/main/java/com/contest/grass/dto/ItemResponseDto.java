package com.contest.grass.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemResponseDto {
    private Long itemId;           // 상품의 고유 ID
    private String itemName;       // 상품명
    private String description;    // 상품 설명
    private Double price;          // 상품 가격
    private String category;       // 상품 카테고리
}
