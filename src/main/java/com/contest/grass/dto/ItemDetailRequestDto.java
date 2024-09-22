package com.contest.grass.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "상품 상세 요청 DTO")
public class ItemDetailRequestDto {

    // 공통 필드
    @Schema(description = "상품명", example = "Apple", required = true)
    private String itemName;       // 상품명

    @Schema(description = "상품 설명", example = "신선한 사과입니다.", required = true)
    private String description;    // 상품 설명

    @Schema(description = "상품 상세 설명", example = "이 사과는 자연에서 자란 최고 품질의 과일입니다.", required = true)
    private String detailedDescription; // 상품 상세 설명

    @Schema(description = "재고 수량", example = "100", required = true)
    private Integer stock;         // 재고

    // 등록 및 업데이트에 사용되는 필드
    @Schema(description = "상품 가격", example = "1500.00", required = true)
    private Double price;          // 상품 가격

    @Schema(description = "상품 카테고리", example = "과일", required = true)
    private String category;       // 상품 카테고리

    // 검색에 사용되는 필드
    @Schema(description = "검색어", example = "Apple", required = false)
    private String searchKeyword;  // 검색어

    @Schema(description = "가격 범위 (최소)", example = "1000.00", required = false)
    private Double minPrice;       // 가격 범위 (최소)

    @Schema(description = "가격 범위 (최대)", example = "2000.00", required = false)
    private Double maxPrice;       // 가격 범위 (최대)
}
