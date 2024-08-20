package com.meltingpot.baeullimflower.post.service;

import com.meltingpot.baeullimflower.post.Repository.PostRepository;
import com.meltingpot.baeullimflower.post.domain.Post;
import com.meltingpot.baeullimflower.post.domain.Status;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class PostStatusScheduler {

    @Autowired
    private PostRepository postRepository;

    @Transactional
    @Scheduled(cron = "0 0 0 * * ?") // 매일 자정에 실행
    public void updatePostStatuses() {
        System.out.println("Scheduled task started..."); // 로그 추가
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startDate = now.minusDays(10).toLocalDate().atStartOfDay(); // 10일 전 00:00
        LocalDateTime endDate = startDate.plusDays(1).minusNanos(1); // 10일 전 23:59:59

        List<Post> posts = postRepository.findAllByCreatedDateBetween(startDate, endDate);


        for (Post post : posts) {
            if (post.getVoteCount() >= 1000) {
                post.updateStatus(Status.DISCUSSION);
            } else {
                post.updateStatus(Status.CONCLUSION);
            }
            postRepository.save(post);
        }
    }
}

