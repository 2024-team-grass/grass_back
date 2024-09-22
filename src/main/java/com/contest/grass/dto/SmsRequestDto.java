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
@Schema(description = "SMS 전송 요청 DTO")
public class SmsRequestDto {

    @Schema(description = "수신자의 전화번호", example = "+821084779186", required = true)
    private String phoneNumber;  // 수신자의 전화번호

    @Schema(description = "전송할 메시지 내용", example = "Your verification code is 123456", required = true)
    private String message;  // 전송할 메시지
}