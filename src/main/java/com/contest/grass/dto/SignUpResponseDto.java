package com.contest.grass.dto;

public class SignUpResponseDto {
    // 등록된 사용자의 이메일
    private String email;

    // 등록된 사용자의 이름
    private String name;

    // 등록된 사용자의 전화번호
    private String phoneNumber;

    // 요청 성공 여부를 나타내는 필드 (true: 성공, false: 실패)
    private boolean success;

    // 요청 처리 결과 메시지 (성공 또는 오류 메시지)
    private String message;

    // 기본 생성자
    public SignUpResponseDto() {}

    // 모든 필드를 포함하는 생성자
    public SignUpResponseDto(String email, String name, String phoneNumber, boolean success, String message) {
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.success = success;
        this.message = message;
    }

    // email 필드의 값을 반환하는 Getter 메서드
    public String getEmail() {
        return email;
    }

    // email 필드의 값을 설정하는 Setter 메서드
    public void setEmail(String email) {
        this.email = email;
    }

    // name 필드의 값을 반환하는 Getter 메서드
    public String getName() {
        return name;
    }

    // name 필드의 값을 설정하는 Setter 메서드
    public void setName(String name) {
        this.name = name;
    }

    // phoneNumber 필드의 값을 반환하는 Getter 메서드
    public String getPhoneNumber() {
        return phoneNumber;
    }

    // phoneNumber 필드의 값을 설정하는 Setter 메서드
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    // success 필드의 값을 반환하는 Getter 메서드
    public boolean isSuccess() {
        return success;
    }

    // success 필드의 값을 설정하는 Setter 메서드
    public void setSuccess(boolean success) {
        this.success = success;
    }

    // message 필드의 값을 반환하는 Getter 메서드
    public String getMessage() {
        return message;
    }

    // message 필드의 값을 설정하는 Setter 메서드
    public void setMessage(String message) {
        this.message = message;
    }
}