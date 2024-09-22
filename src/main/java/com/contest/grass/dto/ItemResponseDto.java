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
@Schema(description = "상품 응답 DTO")
public class ItemResponseDto {

    @Schema(description = "상품의 고유 ID", example = "21", required = true)
    private Long itemId;           // 상품의 고유 ID

    @Schema(description = "상품명", example = "Apple", required = true)
    private String itemName;       // 상품명

    @Schema(description = "상품 설명", example = "신선한 사과입니다.", required = true)
    private String description;    // 상품 설명

    @Schema(description = "상품 가격", example = "1000.00", required = true)
    private Double price;          // 상품 가격

    @Schema(description = "상품 카테고리", example = "과일", required = true)
    private String category;       // 상품 카테고리
}
