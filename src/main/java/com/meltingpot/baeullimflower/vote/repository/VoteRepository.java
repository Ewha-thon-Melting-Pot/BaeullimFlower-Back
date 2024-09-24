package com.meltingpot.baeullimflower.vote.repository;

import com.meltingpot.baeullimflower.member.domain.Member;
import com.meltingpot.baeullimflower.post.domain.Post;
import com.meltingpot.baeullimflower.vote.domain.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    void deleteByMemberAndPost(Member member, Post post);
    Vote findByMemberAndPost(Member member, Post post);
}