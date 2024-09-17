package com.contest.grass.entity;

import com.contest.grass.dto.UserDto;
import com.contest.grass.dto.PostDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonManagedReference;

import java.util.List;
import java.util.stream.Collectors;

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

    // User -> UserDto 변환 메서드 추가
    public UserDto toDto() {
        // Post를 PostDto로 변환
        List<PostDto> postDtos = this.posts.stream()
                .map(post -> new PostDto(post.getPostId(), post.getContent(), post.getCreatedAt().toString()))
                .collect(Collectors.toList());

        // User -> UserDto 변환
        return new UserDto(
                this.getId(),
                this.getName(),
                this.getEmail(),
                this.getNickname(),
                null, // 프로필 이미지 URL (필요하면 추가)
                null, // 주소 (필요하면 추가)
                this.getPassword(),
                postDtos
        );
    }
}
