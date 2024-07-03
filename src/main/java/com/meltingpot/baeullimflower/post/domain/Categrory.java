package com.meltingpot.baeullimflower.post.domain;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Categrory {
    STUDENT_SUPPORT(0, "학생지원"),
    STUDENT_ACTIVITY(1, "학생활동"),
    LIVING_SUPPORT(2, "생활지원"),
    ADMINISTRATIVE_SUPPORT(3,"행정지원"),
    COLLEGE(3, "대학"),
    ETC(4,"기타");

    private final Integer Id;
    private final String title;
}