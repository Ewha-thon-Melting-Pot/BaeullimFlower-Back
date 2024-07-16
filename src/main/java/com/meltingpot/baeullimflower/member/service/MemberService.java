package com.meltingpot.baeullimflower.member.service;

import com.meltingpot.baeullimflower.member.domain.Member;
import com.meltingpot.baeullimflower.member.domain.Role;
import com.meltingpot.baeullimflower.member.dto.SignupRequestDto;
import com.meltingpot.baeullimflower.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Long save(SignupRequestDto requestDto){
        return memberRepository.save(Member.builder()
                .name(requestDto.getName())
                .studentNum(requestDto.getStudentNum())
                .college(requestDto.getCollege())
                .password(bCryptPasswordEncoder.encode(requestDto.getPassword())) //패스워드 암호화
                .role(Role.User)
                .build()).getMemberId();
    }
}
