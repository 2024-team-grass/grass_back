package com.contest.grass.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenDto {
    private boolean valid;
    private String email;

    // 기본 생성자
    public TokenDto() {}

    // 필드 초기화가 포함된 생성자
    public TokenDto(boolean valid, String email) {
        this.valid = valid;
        this.email = email;
    }
}
