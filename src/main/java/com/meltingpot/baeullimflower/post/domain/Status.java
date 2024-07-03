package com.meltingpot.baeullimflower.post.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Status {
    VOTING(0,"진행중","투표 진행중"),
    DISCUSSION(1,"논의중","학생회 논의중"),
    CONCLUSION(2,"완료","완료 및 종료");

    private final Integer Id;
    private final String title;
    private final String description;
}
