package com.contest.grass.dto;

import com.contest.grass.entity.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.format.DateTimeFormatter;
import java.util.Base64;

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

    @Schema(description = "사용자 닉네임", example = "테스트 닉네임")
    private String nickname;

    @Schema(description = "굿버튼", example = "100")
    private int goodbtn;

    @Schema(description = "게시물 공개 여부", example = "true")
    private String isPublic;

    @Schema(description = "이미지 저장", example = "base64_encoded_string")
    private String imageData;  // 변경된 타입



    public PostDto(Long postId, String nickname, String content, int goodbtn, String imageData, String isPublic, String createdAt) {
        this.postId = postId;
        this.nickname = nickname;
        this.content = content;
        this.goodbtn = goodbtn;
        this.imageData = imageData;
        this.isPublic = isPublic;
        this.createdAt = createdAt;
    }

    // Post 엔티티를 PostDto로 변환하는 메소드
    public static PostDto fromEntity(Post post) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        String formattedCreatedAt = post.getCreatedAt() != null ? post.getCreatedAt().format(formatter) : null;
        String imageDataBase64 = post.getImageData() != null ? Base64.getEncoder().encodeToString(post.getImageData()) : null;

        return new PostDto(
                post.getPostId(),
                post.getContent(),
                formattedCreatedAt,
                post.getNickname(),
                post.getGoodbtn(),
                post.getIsPublic(),
                imageDataBase64
        );
    }
}
