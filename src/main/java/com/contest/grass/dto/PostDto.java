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
@Schema(description = "Post DTO representing a user's post")
public class PostDto {

    @Schema(description = "게시물 ID", example = "1")
    private Long postId;

    @Schema(description = "게시물 내용", example = "This is a sample post content.")
    private String content;

    @Schema(description = "게시물 작성 날짜", example = "2024-09-05T12:34:56")
    private String createdAt;
}

