package com.contest.grass.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileResponse {

    private String name;
    private String email;
    private String nickname;
    private String sprouts;
    private List<PostDto> posts;  // 게시물 목록

}
