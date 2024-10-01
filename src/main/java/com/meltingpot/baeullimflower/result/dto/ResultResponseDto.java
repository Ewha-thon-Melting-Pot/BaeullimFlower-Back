package com.meltingpot.baeullimflower.result.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ResultResponseDto {
    private Long postId;
    private Long resultId;
    private String result;
    private LocalDateTime createdAt;

    @Builder
    public ResultResponseDto(Long postId, Long resultId, String result, LocalDateTime createdAt) {
        this.postId = postId;
        this.resultId = resultId;
        this.result = result;
        this.createdAt = createdAt;
    }
}
