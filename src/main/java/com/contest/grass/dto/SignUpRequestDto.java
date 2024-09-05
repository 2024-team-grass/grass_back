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
@Schema(description = "회원가입 요청 DTO")
public class SignUpRequestDto {

    @Schema(description = "사용자의 이메일 주소", example = "user@example.com", required = true)
    private String email;

    @Schema(description = "사용자의 이름", example = "John Doe", required = true)
    private String name;

    @Schema(description = "사용자의 비밀번호", example = "password123", required = true)
    private String password;

    @Schema(description = "비밀번호 확인", example = "password123", required = true)
    private String confirmPassword;

    @Schema(description = "사용자의 전화번호", example = "01012345678", required = true)
    private String phoneNumber;
}
