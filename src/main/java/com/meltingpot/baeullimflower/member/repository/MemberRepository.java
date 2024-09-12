package com.meltingpot.baeullimflower.member.repository;

import com.meltingpot.baeullimflower.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    // 학번 중복 체크
    Boolean existsByStudentNum(String studentNum);
}
