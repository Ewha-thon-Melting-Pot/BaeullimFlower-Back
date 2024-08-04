package com.meltingpot.baeullimflower.member.controller;

import com.meltingpot.baeullimflower.member.dto.LoginRequestDto;
import com.meltingpot.baeullimflower.member.dto.LoginResponseDto;
import com.meltingpot.baeullimflower.member.dto.SignupRequestDto;
import com.meltingpot.baeullimflower.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

   @GetMapping
   public String test(){
       return "test";
   }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequestDto requestDto){
        Long memberId =  memberService.save(requestDto);
        return ResponseEntity.ok("회원가입이 완료되었습니다. 유저 아이디: "+memberId);
    }

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto requestDto){
        return memberService.login(requestDto.getStudentNum(), requestDto.getPassword());
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response){
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return ResponseEntity.ok("로그아웃 되었습니다.");
    }
}
