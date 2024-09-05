package com.contest.grass.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String name;
    private String email;
    private String nickname;
    private String profileImageUrl; // 프로필 사진 URL
    private String address;
    private String password;
}
