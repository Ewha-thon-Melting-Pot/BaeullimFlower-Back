package com.meltingpot.baeullimflower.result.controller;

import com.meltingpot.baeullimflower.global.apiResponse.ApiResponse;
import com.meltingpot.baeullimflower.result.dto.ResultRequestDto;
import com.meltingpot.baeullimflower.result.dto.ResultResponseDto;
import com.meltingpot.baeullimflower.result.service.ResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor // 초기화되지 않은 final 필드나 @NotNull이 붙은 필드에 대해 생성자를 생성해줌
public class ResultController {
    private final ResultService resultService;

    @PostMapping("/{postId}/result")
    public ResultResponseDto createResult(@PathVariable Long postId, @RequestBody ResultRequestDto requestDto){
        return ApiResponse.onCreateSuccess(resultService.createResult(postId,requestDto)).getResult();
    }

}
