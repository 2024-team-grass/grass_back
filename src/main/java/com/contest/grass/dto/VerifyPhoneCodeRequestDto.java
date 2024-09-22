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
@Schema(description = "Verify Phone Code Request DTO representing a request to verify the phone code sent to the user")
public class VerifyPhoneCodeRequestDto {

    @Schema(description = "사용자의 핸드폰 번호", example = "01012345678", required = true)
    private String phoneNumber;

    @Schema(description = "인증 번호", example = "123456", required = true)
    private String code;
}
