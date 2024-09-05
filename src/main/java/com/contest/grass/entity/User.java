package com.contest.grass.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Schema(description = "User entity representing a user in the system")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the user", example = "11")
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    @Schema(description = "User's email address, must be unique", example = "user@example.com")
    private String email;

    @Column(length = 100)
    @Schema(description = "User's password, stored in hashed format", example = "password123")
    private String password;

    @Column(length = 10)
    @Schema(description = "User's name", example = "User1")
    private String name;

    @Column(length = 11)
    @Schema(description = "User's phone number", example = "01012345678")
    private String phoneNumber;

    @Column(unique = true)
    @Schema(description = "User's Kakao ID for social login", example = "kakao_12345")
    private String kakaoId;

    @Column(unique = true)
    @Schema(description = "User's Google ID for social login", example = "google_12345")
    private String googleId;

    @Column(nullable = false, length = 20, unique = true)
    @Schema(description = "User's nickname, must be unique", example = "GrassLover")
    private String nickname;

    @Column(nullable = false)
    @Schema(description = "Number of sprouts (points or levels) the user has", example = "100")
    private Integer sprouts;

    @Column(columnDefinition = "TINYINT(1)")
    @Schema(description = "Indicates whether the user's email is verified", example = "true")
    private boolean isVerified;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Schema(description = "List of posts created by the user")
    private List<Post> posts;

    @Schema(description = "Indicates whether the user's account is enabled", example = "true")
    private boolean enabled = true;
}
