package com.meltingpot.baeullimflower.vote.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class VoteResponseDto {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VoteCreateDto {
        private Long voteId;
        private Long postId;
        private Long memberId;
        private Integer voteCnt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VoteDeleteDto {
        private Long postId;
        private Integer voteCnt;
    }

}
