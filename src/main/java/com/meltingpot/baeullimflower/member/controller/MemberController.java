package com.meltingpot.baeullimflower.member.controller;

import com.meltingpot.baeullimflower.member.dto.LoginRequestDto;
import com.meltingpot.baeullimflower.member.dto.LoginResponseDto;
import com.meltingpot.baeullimflower.member.dto.SignupRequestDto;
import com.meltingpot.baeullimflower.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;
    // 테스트
    @GetMapping("/test")
    public String test() {
        return "test";
    }

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequestDto requestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입이 완료되었습니다. memberid: " + memberService.signup(requestDto));
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto requestDto){
        return ResponseEntity.status(HttpStatus.OK).body(memberService.login(requestDto));
    }
}
