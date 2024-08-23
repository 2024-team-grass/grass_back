package com.contest.grass.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long PostId;

    @Column(length = 10)
    private String nickname;

    @Column(length = 30)
    private String content;

    private Long goodbtn;




    public Long getPostId() {
        return PostId;
    }

    public void setPostId(Long postId) {
        PostId = postId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getGoodbtn() {
        return goodbtn;
    }

    public void setGoodbtn(Long goodbtn) {
        this.goodbtn = goodbtn;
    }


}