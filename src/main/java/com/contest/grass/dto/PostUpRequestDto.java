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
@Schema(description = "게시물 업로드 요청 DTO")
public class PostUpRequestDto {

    @Schema(description = "게시물을 업로드하는 사용자의 닉네임", example = "GrassLover", required = true)
    private String nickname;    // 게시물을 업로드하는 사용자의 닉네임

    @Schema(description = "게시물 사진의 URL 또는 경로", example = "https://example.com/image.jpg")
    private String imageUrl;    // 게시물 사진의 URL 또는 경로

    @Schema(description = "게시물의 공개 여부", example = "true", required = true)
    private boolean isPublic;   // 게시물의 공개 여부 (true: 공개, false: 비공개)

    @Schema(description = "게시물의 내용", example = "오늘은 날씨가 참 좋네요!", required = true)
    private String content;     // 게시물의 내용
}
