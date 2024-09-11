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
@Schema(description = "카카오페이 결제 준비 요청 데이터")
public class KakaoPayReadyRequestDTO {

    @Schema(description = "가맹점 코드 (CID)")
    private String cid;

    @Schema(description = "가맹점 주문 번호")
    private String partnerOrderId;

    @Schema(description = "사용자 ID")
    private String partnerUserId;

    @Schema(description = "상품명")
    private String itemName;

    @Schema(description = "상품 수량")
    private int quantity;

    @Schema(description = "총 결제 금액")
    private int totalAmount;

    @Schema(description = "비과세 금액")
    private int taxFreeAmount;

    @Schema(description = "결제 성공 시 리다이렉트 URL")
    private String approvalUrl;

    @Schema(description = "결제 취소 시 리다이렉트 URL")
    private String cancelUrl;

    @Schema(description = "결제 실패 시 리다이렉트 URL")
    private String failUrl;
}
