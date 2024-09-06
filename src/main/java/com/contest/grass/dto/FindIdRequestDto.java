package com.contest.grass.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "아이디 찾기 요청 DTO")
public class FindIdRequestDto {

    @Schema(description = "사용자의 전화번호", example = "01012345678", required = true)
    private String phoneNumber;
}
