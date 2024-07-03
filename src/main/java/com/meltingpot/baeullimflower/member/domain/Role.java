package com.meltingpot.baeullimflower.member.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    Manager(0,"관리자"),
    User(1,"사용자");

    private final Integer Id;
    private final String title;
}
