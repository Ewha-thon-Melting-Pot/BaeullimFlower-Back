package com.meltingpot.baeullimflower.result.converter;

import com.meltingpot.baeullimflower.member.domain.Member;
import com.meltingpot.baeullimflower.post.domain.Post;
import com.meltingpot.baeullimflower.result.domain.Result;
import com.meltingpot.baeullimflower.result.dto.ResultRequestDto;
import com.meltingpot.baeullimflower.result.dto.ResultResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ResultConverter {
    public Result toResultEntity(ResultRequestDto requestDto, Member member, Post post) {
        Result result = Result.builder()
                .content(requestDto.getContent())
                .manager(member)
                .post(post)
                .build();
        return result;
    }

    public ResultResponseDto toResultDto(Result result) {
        ResultResponseDto resultDto = ResultResponseDto.builder()
                .postId(result.getPost().getPostId())
                .resultId(result.getResultId())
                .result(result.getContent())
                .createdAt(result.getCreatedDate())
                .build();
        return resultDto;
    }
}
