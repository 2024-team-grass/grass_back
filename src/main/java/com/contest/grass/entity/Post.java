package com.contest.grass.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @Column(length = 10)
    private String nickname;

    //공개여부 //public or private
    private String isPublic;

    @Column(length = 30)
    private String content;

    private int goodbtn;

    @Enumerated(EnumType.STRING)
    private PostStatus status;

    private LocalDateTime createdAt;



    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(length = 1000000)
    private byte[] imageData; // 이미지 데이터

    private String imageName; // 이미지 파일 이름
    private String imageType; // 이미지 파일 타입




}
