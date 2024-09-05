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
public class CategoryRequestDto {
    private Long categoryId;         // 카테고리 ID (업데이트 시 필요)
    private String name;             // 카테고리 이름
    private Long parentId;           // 부모 카테고리 ID (최상위 카테고리인 경우 null)
    private List<CategoryRequestDto> childCategories; // 자식 카테고리 목록
}
