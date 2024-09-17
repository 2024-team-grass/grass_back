package com.contest.grass.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @Column(length = 10, nullable = false)
    private String nickname;

    @Column(length = 30, nullable = false)
    private String content;

    public enum LikeStatus {
        LIKED,
        UNLIKED
    }

    @Enumerated(EnumType.STRING)
    private LikeStatus likeStatus = LikeStatus.UNLIKED; //초기 상태는 좋아요가 없는 상태

    private Long goodbtn;

    private LocalDateTime createdAt;

    //게시글이 처음 저장될 때 자동으로 호출되는 메서드
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

}
