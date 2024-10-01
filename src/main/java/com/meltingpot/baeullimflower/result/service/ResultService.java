package com.meltingpot.baeullimflower.result.service;

import com.meltingpot.baeullimflower.global.apiResponse.code.status.ErrorStatus;
import com.meltingpot.baeullimflower.global.apiResponse.exception.GeneralException;
import com.meltingpot.baeullimflower.member.domain.Member;
import com.meltingpot.baeullimflower.member.service.MemberService;
import com.meltingpot.baeullimflower.post.domain.Post;
import com.meltingpot.baeullimflower.post.service.PostService;
import com.meltingpot.baeullimflower.result.converter.ResultConverter;
import com.meltingpot.baeullimflower.result.domain.Result;
import com.meltingpot.baeullimflower.result.dto.ResultRequestDto;
import com.meltingpot.baeullimflower.result.dto.ResultResponseDto;
import com.meltingpot.baeullimflower.result.repository.ResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor// 초기화되지 않은 final 필드나 @NotNull이 붙은 필드에 대해 생성자를 생성해줌
public class ResultService {
    private final PostService postService;
    private final MemberService memberService;
    private final ResultConverter resultConverter;
    private final ResultRepository resultRepository;

    public ResultResponseDto createResult(Long postId, ResultRequestDto requestDto) {
        // 결과를 작성한 관리자 및 청원글 가져오기
        Member manager = memberService.findByStudentNum(MemberService.getCurrentMemberId());
        Post post = postService.findById(postId);

        // 해당 청원글에 결과가 이미 작성되어 있는지 확인
        if(existsByPostId(postId)){
            throw new GeneralException(ErrorStatus.ALREADY_EXIST_RESULT);
        }

        // 결과 객체 생성 및 저장
        Result result = resultConverter.toResultEntity(requestDto, manager, post);
        resultRepository.save(result);

        return resultConverter.toResultDto(result);
    }

    public ResultResponseDto getResult(Long postId) {
        Result result = findByPostId(postId);
        return resultConverter.toResultDto(result);
    }

    /* Transactional 함수들 */

    // 해당 청원글에 결과가 이미 작성되어 있는지 확인
    @Transactional(readOnly = true)
    public boolean existsByPostId(Long postId) {
        return resultRepository.existsByPost_PostId(postId);
    }

    @Transactional(readOnly = true)
    public Result findByPostId(Long postId) {
        return resultRepository.findByPost_PostId(postId);
    }
}
