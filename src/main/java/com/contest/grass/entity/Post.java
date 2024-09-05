package com.contest.grass.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Schema(description = "Post entity representing a user's post in the system")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the post", example = "31")
    private Long postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @Schema(description = "User who created the post")
    private User user;

    @Column(length = 10, nullable = false)
    @Schema(description = "Nickname of the user who created the post", example = "GrassLover")
    private String nickname;

    @Column(length = 30, nullable = false)
    @Schema(description = "Content of the post", example = "This is a post content")
    private String content;

    @Schema(description = "Number of likes or upvotes for the post", example = "15")
    private Long goodbtn;

    @Column(nullable = false)
    @Schema(description = "Date when the post was created", example = "2024-09-03T10:15:30")
    private String createdAt;
}
