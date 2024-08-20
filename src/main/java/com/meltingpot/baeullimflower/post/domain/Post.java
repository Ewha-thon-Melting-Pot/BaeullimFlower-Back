package com.meltingpot.baeullimflower.post.domain;

import com.meltingpot.baeullimflower.global.entity.BaseTimeEntity;
import com.meltingpot.baeullimflower.member.domain.Member;
import com.meltingpot.baeullimflower.url.domain.Url;
import jakarta.persistence.*;
import lombok.*;

import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
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

    @Builder.Default
    @Column(name = "vote_count", nullable = false)
    private Integer voteCount = 0;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Status status = Status.VOTING;

    @Column(nullable = false)
    private String email;

    @Column(name = "info_agree", nullable = false)
    private Boolean infoAgree;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", updatable = false)
    private Member writer;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Url> postUrlList = new ArrayList<>();

    public void updateUrlList(List<String> urls) {
        Set<String> uniqueUrls = new HashSet<>(urls); // 중복 제거
        if (uniqueUrls.isEmpty()) { // URL 리스트가 비어있는 경우
            return;
        }

        postUrlList.clear();

        List<Url> newUrlList = uniqueUrls.stream()
                .map(url -> Url.builder()
                        .url(url)
                        .post(this)
                        .build()) // Url 객체 생성
                .collect(Collectors.toList());

        postUrlList.addAll(newUrlList);
    }



    public List<String> fetchUrlList() {
        List<String> urlLists = new ArrayList<>();
        for (Url url : postUrlList) {
            if (url.getUrl() != null && !url.getUrl().isBlank()) {
                urlLists.add(url.getUrl());
            }
        }
        return urlLists;
    }

    public void updateStatus(Status status){
        this.status = status;
    }



}
