package com.meltingpot.baeullimflower.member.service;

import com.meltingpot.baeullimflower.global.jwt.TokenProvider;
import com.meltingpot.baeullimflower.member.domain.Member;
import com.meltingpot.baeullimflower.member.domain.Role;
import com.meltingpot.baeullimflower.member.dto.LoginResponseDto;
import com.meltingpot.baeullimflower.member.dto.SignupRequestDto;
import com.meltingpot.baeullimflower.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final TokenProvider tokenProvider;

    public Long save(SignupRequestDto requestDto){
        return memberRepository.save(Member.builder()
                .name(requestDto.getName())
                .studentNum(requestDto.getStudentNum())
                .college(requestDto.getCollege())
                .password(bCryptPasswordEncoder.encode(requestDto.getPassword())) //패스워드 암호화
                .role(Role.User)
                .build()).getMemberId();
    }

    public LoginResponseDto login(String studentNum, String password){
        // 입력받은 학번이 존재하는지 확인
        Member member = findByStudentNum(studentNum);
        // 학번은 존재하지만 비밀번호가 일치하는지 확인
        if(!bCryptPasswordEncoder.matches(password, member.getPassword())){
            throw new RuntimeException("잘못된 비밀번호 입니다.");
        }
        // 로그인 성공 -> 토큰 생성
        String accessToken = tokenProvider.generateToken(member, Duration.ofHours(2));
        String refreshToken = "";

        // 결과물을 DTO에 담에 리턴
        return LoginResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

    }

    public Member findById(Long memberId){
        return memberRepository.findById(memberId).orElseThrow(()-> new IllegalArgumentException("Unexpected Member"));
    }

    public Member findByStudentNum(String studentNum) {
        return memberRepository.findByStudentNum(studentNum).orElseThrow(()-> new IllegalArgumentException("존재하지 않는 학번입니다."));
    }


}
