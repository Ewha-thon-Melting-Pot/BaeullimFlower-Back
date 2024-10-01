package com.meltingpot.baeullimflower.result.controller;

import com.meltingpot.baeullimflower.global.apiResponse.ApiResponse;
import com.meltingpot.baeullimflower.result.dto.ResultRequestDto;
import com.meltingpot.baeullimflower.result.dto.ResultResponseDto;
import com.meltingpot.baeullimflower.result.service.ResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor // 초기화되지 않은 final 필드나 @NotNull이 붙은 필드에 대해 생성자를 생성해줌
public class ResultController {
    private final ResultService resultService;

    // 논의결과 작성
    @PostMapping("admin/{postId}/result")
    public ResultResponseDto createResult(@PathVariable Long postId, @RequestBody ResultRequestDto requestDto){
        return ApiResponse.onCreateSuccess(resultService.createResult(postId,requestDto)).getResult();
    }

    // 논의결과 조회
    @GetMapping("/{postId}/result")
    public ResultResponseDto getResult(@PathVariable Long postId){
        return ApiResponse.onSuccess(resultService.getResult(postId)).getResult();
    }


}
