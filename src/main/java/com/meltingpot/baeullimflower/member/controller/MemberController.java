package com.meltingpot.baeullimflower.member.controller;

import com.meltingpot.baeullimflower.global.apiResponse.ApiResponse;
import com.meltingpot.baeullimflower.member.dto.LoginRequestDto;
import com.meltingpot.baeullimflower.member.dto.LoginResponseDto;
import com.meltingpot.baeullimflower.member.dto.SignupRequestDto;
import com.meltingpot.baeullimflower.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;
    // 테스트
    @GetMapping("/test")
    public String test() {
        Long memberid = MemberService.getCurrentMemberId();
        return "test";
    }

    // 회원가입
    @PostMapping("/signup")
    public ApiResponse<String> signup(@RequestBody SignupRequestDto requestDto){
        return ApiResponse.onCreateSuccess("회원가입이 완료되었습니다. memberId: "+ memberService.signup(requestDto));
    }

    // 로그인
    @PostMapping("/login")
    public ApiResponse<LoginResponseDto> login(@RequestBody LoginRequestDto requestDto){
        return ApiResponse.onSuccess(memberService.login(requestDto));
    }
}
