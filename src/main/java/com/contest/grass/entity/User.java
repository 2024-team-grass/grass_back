package com.contest.grass.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Schema(description = "User entity representing a user in the system")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the user", example = "1")
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    @Schema(description = "User's email address, must be unique", example = "user@example.com")
    private String email;

    @Column(length = 100)
    @Schema(description = "User's password, stored in hashed format", example = "password123")
    private String password;

    @Column(length = 10)
    @Schema(description = "User's name", example = "John Doe")
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

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getKakaoId() {
        return kakaoId;
    }

    public void setKakaoId(String kakaoId) {
        this.kakaoId = kakaoId;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getSprouts() {
        return sprouts;
    }

    public void setSprouts(Integer sprouts) {
        this.sprouts = sprouts;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setIsVerified(boolean isVerified) {
        this.isVerified = isVerified;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
