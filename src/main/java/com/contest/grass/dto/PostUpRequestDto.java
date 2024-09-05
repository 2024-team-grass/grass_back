package com.contest.grass.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostUpRequestDto {
    private String nickname;    // 게시물을 업로드하는 사용자의 닉네임
    private String imageUrl;    // 게시물 사진의 URL 또는 경로
    private boolean isPublic;   // 게시물의 공개 여부 (true: 공개, false: 비공개)
    private String content;     // 게시물의 내용
}
