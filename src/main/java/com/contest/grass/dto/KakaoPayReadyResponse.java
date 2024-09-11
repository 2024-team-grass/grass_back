package com.contest.grass.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoPayReadyResponse {
    private String tid;  // 결제 고유 번호
    private String next_redirect_pc_url;  // 사용자에게 보여줄 결제 페이지 URL
}
