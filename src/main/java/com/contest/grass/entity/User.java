package com.contest.grass.entity;

public class User {
    private String id;
    private String email;
    private String KakaoId;
    private String GoogleId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getKakaoId() {
        return KakaoId;
    }

    public void setKakaoId(String kakaoId) {
        KakaoId = kakaoId;
    }

    public String getGoogleId() {
        return GoogleId;
    }

    public void setGoogleId(String googleId) {
        GoogleId = googleId;
    }
}
