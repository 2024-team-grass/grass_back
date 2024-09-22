package com.contest.grass.entity;


import com.contest.grass.dto.PostDto;
import com.contest.grass.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Column(length = 100)
    private String password;

    private Long todayPost;

    @Column(name = "total_user")
    private Long total;

    private Long dayOfTheWeekPost;

    @Column(length = 10)
    private String name;

    @Column(length = 23)
    private String phoneNumber;

    @Column(nullable = false, length = 20, unique = true)
    private String nickname;

    @Column(nullable = false)
    private String sprouts;

    private String ProfileImageUrl;

    @OneToMany(mappedBy = "user")
    private List<Week> weeks; // User와 Week 간의 관계

    @Embedded
    private Address address;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Orders> orders = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = LAZY, cascade = CascadeType.ALL)
    private List<Post> posts;

    private boolean enabled = true;



}
