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
@Schema(description = "회원가입 요청에 대한 응답 DTO")
public class SignUpResponseDto {

    @Schema(description = "등록된 사용자의 이메일", example = "user@example.com", required = true)
    private String email;          // 등록된 사용자의 이메일

    @Schema(description = "등록된 사용자의 이름", example = "John Doe", required = true)
    private String name;           // 등록된 사용자의 이름

    @Schema(description = "등록된 사용자의 전화번호", example = "01012345678", required = true)
    private String phoneNumber;    // 등록된 사용자의 전화번호

    @Schema(description = "요청 성공 여부 (true: 성공, false: 실패)", example = "true", required = true)
    private boolean success;       // 요청 성공 여부 (true: 성공, false: 실패)

    @Schema(description = "요청 처리 결과 메시지 (성공 또는 오류 메시지)", example = "회원가입이 성공적으로 완료되었습니다.", required = true)
    private String message;        // 요청 처리 결과 메시지 (성공 또는 오류 메시지)
}
