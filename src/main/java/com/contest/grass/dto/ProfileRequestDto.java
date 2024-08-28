package com.contest.grass.dto;

public class ProfileRequestDto {
    private String name;
    private String email;
    private String nickname;
    private String profileImageUrl; // 프로필 사진 URL
    private String address;
    private String password;

    // 기본 생성자
    public ProfileRequestDto() {}

    // 모든 필드를 포함하는 생성자
    public ProfileRequestDto(String name, String email, String nickname, String profileImageUrl, String address, String password) {
        this.name = name;
        this.email = email;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.address = address;
        this.password = password;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}