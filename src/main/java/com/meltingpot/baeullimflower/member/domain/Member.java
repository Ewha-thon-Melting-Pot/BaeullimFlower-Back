package com.meltingpot.baeullimflower.member.domain;

import com.meltingpot.baeullimflower.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue
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
}
