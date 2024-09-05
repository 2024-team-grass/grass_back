package com.contest.grass.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpResponseDto {
    private String email;          // 등록된 사용자의 이메일
    private String name;           // 등록된 사용자의 이름
    private String phoneNumber;    // 등록된 사용자의 전화번호
    private boolean success;       // 요청 성공 여부 (true: 성공, false: 실패)
    private String message;        // 요청 처리 결과 메시지 (성공 또는 오류 메시지)
}
