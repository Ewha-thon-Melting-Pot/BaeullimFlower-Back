package com.meltingpot.baeullimflower.vote.domain;

import com.meltingpot.baeullimflower.member.domain.Member;
import com.meltingpot.baeullimflower.post.domain.Post;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vote_id", updatable = false)
    private Long voteId;

    @ManyToOne
    @JoinColumn(name = "post_id", updatable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "member_id", updatable = false)
    private Member member;
}
