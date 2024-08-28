package com.contest.grass.dto;

public class PostUpResponseDto {
    // 업로드된 게시물의 고유 ID
    private Long postId;

    // 업로드된 게시물의 URL 또는 경로
    private String imageUrl;

    // 게시물의 공개 여부 (true: 공개, false: 비공개)
    private boolean isPublic;

    // 업로드 결과를 나타내는 메시지 (예: "업로드 성공", "업로드 실패")
    private String message;

    // 업로드 성공 여부 (true: 성공, false: 실패)
    private boolean success;

    // 기본 생성자
    public PostUpResponseDto() {}

    // 모든 필드를 포함하는 생성자
    public PostUpResponseDto(Long postId, String imageUrl, boolean isPublic, String message, boolean success) {
        this.postId = postId;
        this.imageUrl = imageUrl;
        this.isPublic = isPublic;
        this.message = message;
        this.success = success;
    }

    // postId 필드의 값을 반환하는 Getter 메서드
    public Long getPostId() {
        return postId;
    }

    // postId 필드의 값을 설정하는 Setter 메서드
    public void setPostId(Long postId) {
        this.postId = postId;
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

    // message 필드의 값을 반환하는 Getter 메서드
    public String getMessage() {
        return message;
    }

    // message 필드의 값을 설정하는 Setter 메서드
    public void setMessage(String message) {
        this.message = message;
    }

    // success 필드의 값을 반환하는 Getter 메서드
    public boolean isSuccess() {
        return success;
    }

    // success 필드의 값을 설정하는 Setter 메서드
    public void setSuccess(boolean success) {
        this.success = success;
    }
}