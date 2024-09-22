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
@Schema(description = "게시물 업로드 응답 DTO")
public class PostUpResponseDto {

    @Schema(description = "업로드된 게시물의 고유 ID", example = "12345", required = true)
    private Long postId;        // 업로드된 게시물의 고유 ID

    @Schema(description = "업로드된 게시물의 URL 또는 경로", example = "https://example.com/image.jpg")
    private String imageUrl;    // 업로드된 게시물의 URL 또는 경로

    @Schema(description = "게시물의 공개 여부", example = "true", required = true)
    private boolean isPublic;   // 게시물의 공개 여부 (true: 공개, false: 비공개)

    @Schema(description = "업로드 결과를 나타내는 메시지", example = "업로드 성공", required = true)
    private String message;     // 업로드 결과를 나타내는 메시지 (예: "업로드 성공", "업로드 실패")

    @Schema(description = "업로드 성공 여부", example = "true", required = true)
    private boolean success;    // 업로드 성공 여부 (true: 성공, false: 실패)
}
