package com.contest.grass.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "카카오페이 결제 준비 응답 데이터")
public class KakaoPayReadyResponseDTO {

    @Schema(description = "결제 고유 번호")
    private String tid;

    @Schema(description = "결제 페이지로 리다이렉트할 URL")
    private String nextRedirectPcUrl;

    @Schema(description = "결제 준비 완료 시간")
    private String createdAt;
}
