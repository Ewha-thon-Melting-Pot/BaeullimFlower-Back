package com.meltingpot.baeullimflower.member.repository;

import com.meltingpot.baeullimflower.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByStudentNum(String studentNum);
}
