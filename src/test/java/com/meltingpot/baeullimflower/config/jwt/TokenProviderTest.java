package com.meltingpot.baeullimflower.config.jwt;

import com.meltingpot.baeullimflower.member.domain.College;
import com.meltingpot.baeullimflower.member.domain.Member;
import com.meltingpot.baeullimflower.member.domain.Role;
import com.meltingpot.baeullimflower.member.repository.MemberRepository;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Duration;
import java.util.Date;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TokenProviderTest {

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private JwtProperties jwtProperties;

    // 1. generateToken() 검증 테스트
    @DisplayName("generateToken(): 유저 정보와 만료 기간을 전달해 토큰을 만들 수 있다.")
    @Test
    void generateToken() {
        // given: 토큰에 유저 정보를 추가하기위한 테스트 유저 만들기
        Member testMember = memberRepository.save(Member.builder()
                .name("테스트")
                .studentNum("12345678")
                .password("test")
                .college(College.Engineering)
                .role(Role.User)
                .build());

        // when: generateToken() 메서드를 호출해 토큰 생성
        String token = tokenProvider.generateToken(testMember, Duration.ofDays(14));

        // then: 토큰 복호화 후 토큰을 만들때 클레임으로 넣어둔 id 값이 given 절에서 만든 유저 ID와 동일한지 확인
        Long userId = Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("id", Long.class);

        assertThat(userId).isEqualTo(testMember.getMemberId());
    }

    // 2. validToken() 검증 테스트 (실패)
    @DisplayName("validToken(): 만료된 토큰인 경우에 유효성 검증에 실패한다.")
    @Test
    void validToken_invalidToken() {
        // given: 만료시간을 7일 뺀 값으로 지정하여 이미 만료된 토큰 생성
        String token = JwtFactory.builder()
                .expiration(new Date(new Date().getTime() - Duration.ofDays(7).toMillis()))
                .build()
                .createToken(jwtProperties);

        // when: validToken() 메서드를 호출해 유효한 토큰인지 검증
        boolean result = tokenProvider.validateToken(token);

        // then: 반환값이 false인지 확인
        assertThat(result).isFalse();
    }

    // 2. validToken() 검증 테스트 (성공)
    @DisplayName("validToken(): 유효한 토큰인 경우에 유효성 검증에 성공한다.")
    @Test
    void validToken_validToken() {
        // given: 만료시간을 현재 시간으로부터 14일 뒤로 지정하여 만료되지 않은 토큰 생성
        String token = JwtFactory.withDefaultValues()
                .createToken(jwtProperties);

        // when: validToken() 메서드를 호출해 유효한 토큰인지 검증
        boolean result = tokenProvider.validateToken(token);

        // then: 반환값이 true인지 확인
        assertThat(result).isTrue();
    }


    // 3. getAuthentication() 검증 테스트
    @DisplayName("getAuthentication(): 토큰 기반으로 인증정보를 가져올 수 있다.")
    @Test
    void getAuthentication() {
        // given: 토큰 대상자인 학번을 12345678로 지정하여 토큰 생성
        String memberStudentNum = "12345678";
        Role role = Role.User;
        String token = JwtFactory.builder()
                .subject(memberStudentNum)
                .role(role)
                .build()
                .createToken(jwtProperties);

        // when: getAuthentication() 메서드를 호출해 인증 객체 반환
        Authentication authentication = tokenProvider.getAuthentication(token);

        // then: 반환받은 인증 객체의 학번과 given 절에서 설정한 학번이 같은지 확인
        assertThat(((UserDetails) authentication.getPrincipal()).getUsername()).isEqualTo(memberStudentNum);
    }

    // 4. getUserId() 검증 테스트
    @DisplayName("getUserId(): 토큰으로 유저 ID를 가져올 수 있다.")
    @Test
    void getUserId() {
        // given: 유저ID 값이 1인 클레임을 추가하여 토큰 생성
        Long userId = 1L;
        String token = JwtFactory.builder()
                .claims(Map.of("id", userId))
                .build()
                .createToken(jwtProperties);

        // when: getUserId() 메서드를 호출해 유저ID 반환
        Long userIdByToken = tokenProvider.getUserId(token);

        // then: 반환받은 유저ID가 given절에서 설정한 유저ID와 같은지 확인
        assertThat(userIdByToken).isEqualTo(userId);
    }
}