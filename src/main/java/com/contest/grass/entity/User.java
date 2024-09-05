package com.contest.grass.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Column(length = 100)
    private String password;

    @Column(length = 10)
    private String name;

    @Column(length = 11)
    private String phoneNumber;

    @Column(unique = true)
    private String kakaoId;

    @Column(unique = true)
    private String googleId;

    @Column(nullable = false, length = 20, unique = true)
    private String nickname;

    @Column(nullable = false)
    private Integer sprouts = 0;

    @Column(columnDefinition = "TINYINT(1)")
    private boolean isVerified;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Post> posts;

    private boolean enabled = true;
}
