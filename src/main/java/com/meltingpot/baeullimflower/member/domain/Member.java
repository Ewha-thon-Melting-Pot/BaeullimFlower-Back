package com.meltingpot.baeullimflower.member.domain;

import com.meltingpot.baeullimflower.vote.domain.Vote;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

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

    @Column(name = "student_num", nullable = false, length = 16)
    private String studentNum;

    @Enumerated(EnumType.STRING)
    private College college;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Vote> voteList;

    @Builder
    public Member(String name, String studentNum, College college, String password, Role role) {
        this.name = name;
        this.studentNum = studentNum;
        this.college = college;
        this.password = password;
        this.role = role;
    }
}
