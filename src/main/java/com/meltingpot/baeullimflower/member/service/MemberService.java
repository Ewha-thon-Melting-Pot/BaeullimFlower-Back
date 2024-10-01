package com.meltingpot.baeullimflower.member.service;

import com.meltingpot.baeullimflower.global.apiResponse.code.status.ErrorStatus;
import com.meltingpot.baeullimflower.global.apiResponse.exception.GeneralException;
import com.meltingpot.baeullimflower.global.jwt.JwtAuthenticationProvider;
import com.meltingpot.baeullimflower.member.domain.Member;
import com.meltingpot.baeullimflower.member.domain.Role;
import com.meltingpot.baeullimflower.member.dto.LoginRequestDto;
import com.meltingpot.baeullimflower.member.dto.LoginResponseDto;
import com.meltingpot.baeullimflower.member.dto.SignupRequestDto;
import com.meltingpot.baeullimflower.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtAuthenticationProvider jwtProvider;

    // 회원가입
    public Long signup(SignupRequestDto requestDto){
        // 학번 중복 체크
        if(existsByStudentNum(requestDto.getStudentNum())){
            throw new GeneralException(ErrorStatus.ALREADY_EXIST_STUDENTNUM);
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

    // 로그인
    public LoginResponseDto login(LoginRequestDto requestDto){
        // 사용자 가져오기
        Member member = findByStudentNum(requestDto.getStudentNum());

        // 학번 존재 여부 확인
        if(member == null){
            throw new GeneralException(ErrorStatus.NOT_EXIST_STUDENTNUM);
        }

        // 비밀번호 일치여부 확인
        if(!bCryptPasswordEncoder.matches(requestDto.getPassword(), member.getPassword())){
            throw new GeneralException(ErrorStatus.MISMATCH_PASSWORD);
        }

        /*
        로그인 성공 -> 토큰 생성
        - 액세스토큰: 30분
        - 리프레시토큰: 7일
        */
        String accessToken = jwtProvider.generateToken(member, Duration.ofMinutes(30));
        String refreshToken = jwtProvider.generateToken(member, Duration.ofDays(7));

        // 리프레시 토큰 저장

        return LoginResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    // 현재 인증된(로그인한) 유저의 id를 찾아 반환
    public static String getCurrentMemberId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication.getName() == null){
            throw new GeneralException(ErrorStatus.MEMBER_NOT_FOUND);
//            throw new AuthenticationServiceException("인증된 사용자정보가 없습니다.");
        }
        return authentication.getName();
    }


    /* Transactional 함수들 */

    // 학번 중복 체크
    @Transactional(readOnly = true)
    public Boolean existsByStudentNum(String studentNum){
        return memberRepository.existsByStudentNum(studentNum);
    }

    // 학번으로 사용자 조회
    @Transactional(readOnly = true)
    public Member findByStudentNum(String studentNum){
        return memberRepository.findByStudentNum(studentNum);
    }
}
