package com.contest.grass.dto;

public class PostUpRequestDto {
    // 게시물을 업로드하는 사용자의 닉네임
    private String nickname;

    // 게시물 사진의 URL 또는 경로
    private String imageUrl;

    // 게시물의 공개 여부 (true: 공개, false: 비공개)
    private boolean isPublic;

    // 게시물의 내용
    private String content;

    // 기본 생성자
    public PostUpRequestDto() {}

    // 모든 필드를 포함하는 생성자
    public PostUpRequestDto(String nickname, String imageUrl, boolean isPublic, String content) {
        this.nickname = nickname;
        this.imageUrl = imageUrl;
        this.isPublic = isPublic;
        this.content = content;
    }

    // nickname 필드의 값을 반환하는 Getter 메서드
    public String getNickname() {
        return nickname;
    }

    // nickname 필드의 값을 설정하는 Setter 메서드
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    // imageUrl 필드의 값을 반환하는 Getter 메서드
    public String getImageUrl() {
        return imageUrl;
    }

    // imageUrl 필드의 값을 설정하는 Setter 메서드
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    // isPublic 필드의 값을 반환하는 Getter 메서드
    public boolean isPublic() {
        return isPublic;
    }

    // isPublic 필드의 값을 설정하는 Setter 메서드
    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    // content 필드의 값을 반환하는 Getter 메서드
    public String getContent() {
        return content;
    }

    // content 필드의 값을 설정하는 Setter 메서드
    public void setContent(String content) {
        this.content = content;
    }
}