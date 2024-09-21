package com.meltingpot.baeullimflower.member.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", updatable = false)
    private Long memberId;

    @Column(nullable = false, length = 16)
    private String name;

    @Column(name = "stu_num", nullable = false, length = 16)
    private String studentNum;

    @Enumerated(EnumType.STRING)
    private College college;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public Member(String name, String studentNum, College college, String password, Role role) {
        this.name = name;
        this.studentNum = studentNum;
        this.college = college;
        this.password = password;
        this.role = role;
    }
}
