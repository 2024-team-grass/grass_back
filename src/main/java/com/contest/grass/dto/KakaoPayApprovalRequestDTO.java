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
@Schema(description = "카카오페이 결제 승인 요청 데이터")
public class KakaoPayApprovalRequestDTO {

    @Schema(description = "가맹점 코드 (CID)")
    private String cid;

    @Schema(description = "결제 고유 번호 (TID)")
    private String tid;

    @Schema(description = "가맹점 주문 번호")
    private String partnerOrderId;

    @Schema(description = "사용자 ID")
    private String partnerUserId;

    @Schema(description = "결제 성공 후 PG 토큰")
    private String pgToken;
}
