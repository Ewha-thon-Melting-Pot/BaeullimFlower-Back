package com.meltingpot.baeullimflower.vote.domain;

import com.meltingpot.baeullimflower.member.domain.Member;
import com.meltingpot.baeullimflower.post.domain.Post;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vote_id", updatable = false)
    private Long voteId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", updatable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", updatable = false)
    private Member member;
}
