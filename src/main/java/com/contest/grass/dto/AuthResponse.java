package com.contest.grass.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "인증 요청에 대한 응답 DTO")
public class AuthResponse {

    @Schema(description = "JWT 인증 토큰", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9")
    private String token;

    @Schema(description = "사용자 이름", example = "우동엽")
    private String name;

    @Schema(description = "사용자 포인트", example = "123,123")
    private String sprouts;

    @Schema(description = "사용자 닉네임", example = "테스트닉네임")
    private String nickname;

    @Schema(description = "사용자 아이디", example = "1")
    private Long userId;

}
