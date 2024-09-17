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
@Schema(description = "User DTO representing a user in the system")
public class UserDto {

    private Long id;

    @Schema(description = "사용자의 이름", example = "John Doe", required = true)
    private String name;

    @Schema(description = "사용자의 이메일 주소", example = "user@example.com", required = true)
    private String email;

    @Schema(description = "사용자의 닉네임", example = "GrassLover", required = true)
    private String nickname;

    @Schema(description = "프로필 사진의 URL", example = "https://example.com/profile.jpg")
    private String profileImageUrl;

    @Schema(description = "사용자의 주소", example = "서울특별시 강남구 테헤란로 123")
    private String address;

    @Schema(description = "사용자의 비밀번호", example = "password123", required = true)
    private String password;

    @Schema(description = "사용자가 작성한 게시물 리스트")
    private List<PostDto> posts; // 게시물 리스트 추가
}
