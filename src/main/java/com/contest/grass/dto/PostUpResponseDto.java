package com.contest.grass.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostUpResponseDto {
    private Long postId;        // 업로드된 게시물의 고유 ID
    private String imageUrl;    // 업로드된 게시물의 URL 또는 경로
    private boolean isPublic;   // 게시물의 공개 여부 (true: 공개, false: 비공개)
    private String message;     // 업로드 결과를 나타내는 메시지 (예: "업로드 성공", "업로드 실패")
    private boolean success;    // 업로드 성공 여부 (true: 성공, false: 실패)
}
