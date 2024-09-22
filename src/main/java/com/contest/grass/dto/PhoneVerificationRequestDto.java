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
@Schema(description = "Phone Verification Request DTO representing a request to send a verification code to the user's phone number")
public class PhoneVerificationRequestDto {

    @Schema(description = "사용자의 핸드폰 번호", example = "01012345678", required = true)
    private String phoneNumber;
}
