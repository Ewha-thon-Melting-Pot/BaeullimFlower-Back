package com.meltingpot.baeullimflower.post.dto;

import com.meltingpot.baeullimflower.member.domain.Member;
import com.meltingpot.baeullimflower.post.domain.Categrory;
import com.meltingpot.baeullimflower.post.domain.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.w3c.dom.stylesheets.LinkStyle;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class PostResponseDto {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostDto {
        private Long postId;
        private Member writer;

        private String email;

        private Status status;
        private String title;
        private String content;
        private Categrory categrory;
        private Integer voteCount;

        private LocalDateTime createdDate;
        private LocalDateTime deadline;
        private Duration dDay;

        List<String> urlList;
    }
}
