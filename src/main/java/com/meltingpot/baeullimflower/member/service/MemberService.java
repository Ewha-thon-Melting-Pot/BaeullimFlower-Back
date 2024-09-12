package com.meltingpot.baeullimflower.member.service;

import com.meltingpot.baeullimflower.member.domain.Member;
import com.meltingpot.baeullimflower.member.domain.Role;
import com.meltingpot.baeullimflower.member.dto.SignupRequestDto;
import com.meltingpot.baeullimflower.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // 회원가입
    public Long signup(SignupRequestDto requestDto){
        // 학번 중복 체크
        if(existsByStudentNum(requestDto.getStudentNum())){
            throw new RuntimeException("이미 존재하는 학번입니다.");
        }

        // 중복되지 않는다면 저장
        return memberRepository.save(Member.builder()
                .name(requestDto.getName())
                .studentNum(requestDto.getStudentNum())
                .college(requestDto.getCollege())
                .password(bCryptPasswordEncoder.encode(requestDto.getPassword())) //패스워드 암호화
                .role(Role.User)
                .build()).getMemberId();
    }

    /* Transactional 함수들 */

    // 학번 중복 체크
    @Transactional(readOnly = true)
    public Boolean existsByStudentNum(String studentNum){
        return memberRepository.existsByStudentNum(studentNum);
    }
}
