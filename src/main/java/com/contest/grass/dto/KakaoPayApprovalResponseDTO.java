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
@Schema(description = "카카오페이 결제 승인 응답 데이터")
public class KakaoPayApprovalResponseDTO {

    @Schema(description = "요청 고유 번호 (AID)")
    private String aid;

    @Schema(description = "결제 고유 번호 (TID)")
    private String tid;

    @Schema(description = "가맹점 코드 (CID)")
    private String cid;

    @Schema(description = "결제 상태")
    private String status;

    @Schema(description = "결제 수단")
    private String paymentMethodType;

    @Schema(description = "결제 금액 정보")
    private String amount;

    @Schema(description = "결제 승인 시간")
    private String approvedAt;
}