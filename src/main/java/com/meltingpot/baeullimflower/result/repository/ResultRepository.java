package com.meltingpot.baeullimflower.result.repository;

import com.meltingpot.baeullimflower.result.domain.Result;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResultRepository extends JpaRepository<Result, Long> {
    Boolean existsByPost_PostId(Long postId);
}
