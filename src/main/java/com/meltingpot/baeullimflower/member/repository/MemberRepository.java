package com.meltingpot.baeullimflower.member.repository;

import com.meltingpot.baeullimflower.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    // 학번 중복 체크
    Boolean existsByStudentNum(String studentNum);

    // 학번으로 Member 찾기
    Member findByStudentNum(String studentNum);
}
