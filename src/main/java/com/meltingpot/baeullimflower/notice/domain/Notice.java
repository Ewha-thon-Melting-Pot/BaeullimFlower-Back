package com.meltingpot.baeullimflower.notice.domain;


import com.meltingpot.baeullimflower.post.domain.Post;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id", updatable = false)
    private Long noticeId;

    @ManyToOne
    @JoinColumn(name = "post_id", updatable = false)
    private Post post;
}
