package com.meltingpot.baeullimflower.post.domain;

import com.meltingpot.baeullimflower.global.entity.BaseTimeEntity;
import com.meltingpot.baeullimflower.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", updatable = false)
    private Long postId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    private Categrory categrory;

    @Column(name = "vote_count", nullable = false)
    private Integer voteCount;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(nullable = false)
    private LocalDateTime deadline;

    @Column(nullable = false)
    private String email;

    @Column(name = "info_agree", nullable = false)
    private Boolean infoAgree;

    @ManyToOne
    @JoinColumn(name = "member_id", updatable = false)
    private Member member;
}
