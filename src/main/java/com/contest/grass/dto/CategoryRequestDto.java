package com.contest.grass.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "카테고리 요청 DTO")
public class CategoryRequestDto {

    @Schema(description = "카테고리 ID (업데이트 시 필요)", example = "61")
    private Long categoryId;

    @Schema(description = "카테고리 이름", example = "Electronics", required = true)
    private String name;

    @Schema(description = "부모 카테고리 ID (최상위 카테고리인 경우 null)", example = "null")
    private Long parentId;

    @Schema(description = "자식 카테고리 목록")
    private List<CategoryRequestDto> childCategories;
}
