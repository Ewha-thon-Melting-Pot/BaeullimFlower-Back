package com.meltingpot.baeullimflower.post.Repository;

import com.meltingpot.baeullimflower.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByCreatedDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
