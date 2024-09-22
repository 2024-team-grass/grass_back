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
@Schema(description = "SMS 인증 코드 검증 요청 DTO")
public class SmsVerificationDto {

    @Schema(description = "인증을 시도하는 사용자의 전화번호", example = "+821084779186", required = true)
    private String phoneNumber;  // 인증을 시도하는 사용자의 전화번호

    @Schema(description = "사용자가 입력한 인증 코드", example = "123456", required = true)
    private String verificationCode;  // 입력한 인증 코드
}