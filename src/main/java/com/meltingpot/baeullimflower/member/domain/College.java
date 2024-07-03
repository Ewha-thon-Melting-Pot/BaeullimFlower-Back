package com.meltingpot.baeullimflower.member.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum College {
    LiberalArts(0,"인문과학대학"),
    SocialSciences(1,"사회과학대학"),
    NaturalSciences(2,"자연과학대학"),
    Engineering(3,"공과대학"),
    Music(4,"음악대학"),
    ArtDesign(5,"조형예술대학"),
    Education(6,"사범대학"),
    BusinessAdministration(7,"경영대학"),
    ScienceIndustryConvergence(8,"신산업융합대학"),
    Medicine(9,"의과대학"),
    Nursing(10,"간호대학"),
    Pharmacy(11,"약학대학"),
    Scranton(12,"스크랜튼대학"),
    ArtificialIntelligence(13,"인공지능대학"),
    HOKMA(14,"호크마교양대학");

    private final Integer Id;
    private final String title;
}
