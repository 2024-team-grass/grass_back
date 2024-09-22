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
@Schema(description = "주문 응답 DTO")
public class OrdersResponseDto {

    @Schema(description = "주문 ID", example = "12345", required = true)
    private Long orderId;

    @Schema(description = "주문자 이름", example = "John Doe", required = true)
    private String name;

    @Schema(description = "주문자의 전화번호", example = "01012345678", required = true)
    private Integer phoneNumber;

    @Schema(description = "주문 배송지 주소", example = "서울특별시 강남구 테헤란로 123", required = true)
    private String address;

    @Schema(description = "상세주소", example = "101호", required = true)
    private String detailAddress;

    @Schema(description = "주문 관련 요청사항", example = "문 앞에 두고 가주세요")
    private String request;

    @Schema(description = "공동현관 출입번호", example = "1234")
    private String doorpassword;

    @Schema(description = "결제 수단", example = "NPay", required = true)
    private String paymentMethod;

    @Schema(description = "주문 총액", example = "50000", required = true)
    private Integer totalAmount;

    @Schema(description = "주문 상태", example = "배송 중", required = true)
    private String status;

    @Schema(description = "응답 메시지", example = "주문이 성공적으로 접수되었습니다.", required = true)
    private String message;
}
