package com.meltingpot.baeullimflower.member.service;

import com.meltingpot.baeullimflower.member.domain.Member;
import com.meltingpot.baeullimflower.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
//스프링 시큐리티에서 사용자 정보를 가져오는 인터페이스
public class MemberDetailService implements UserDetailsService {
    private final MemberRepository memberRepository;

    //사용자 이름(studentNum)으로 사용자의 정보를 가져오는 메서드
    @Override
    public Member loadUserByUsername(String strudentNum){
        return memberRepository.findByStudentNum(strudentNum)
                .orElseThrow(() -> new UsernameNotFoundException(strudentNum));
    }
}
