package com.contest.grass.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponseDto {
    private Long categoryId;         // 카테고리 ID
    private String name;             // 카테고리 이름
    private Long parentId;           // 부모 카테고리 ID (최상위 카테고리인 경우 null)
    private List<CategoryResponseDto> childCategories; // 자식 카테고리 목록
}
