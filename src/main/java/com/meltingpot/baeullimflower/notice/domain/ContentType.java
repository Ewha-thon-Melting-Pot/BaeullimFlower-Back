package com.meltingpot.baeullimflower.notice.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ContentType {
    REACHING(1,"인원수달성","목표 인원수를 달성했습니다."),
    DISCUSSION(2,"투표마감","투표가 마감되었습니다."),
    CONCLUSION(3,"논의완료","논의가 완료되었습니다.");

    private final Integer Id;
    private final String title;
    private final String description;
}
