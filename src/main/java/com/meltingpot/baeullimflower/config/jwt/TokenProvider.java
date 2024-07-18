package com.meltingpot.baeullimflower.config.jwt;

import com.meltingpot.baeullimflower.member.domain.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;

@RequiredArgsConstructor
@Service
public class TokenProvider {
    private final JwtProperties jwtProperties;

    public String generateToken(Member member, Duration expiredAt) {
        Date now = new Date();

        return makeToken(new Date(now.getTime() + expiredAt.toMillis()), member);
    }

    // JWT 토큰 생성 메서드
    private String makeToken(Date expiry, Member member){
        Date now = new Date();

        return Jwts.builder()
                // 헤더 - 토큰 타입(typ): JWT
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                // 내용 - 토큰 발급자(iss): meltingpot@gmail.com (properties에서 설정한 값)
                .setIssuer(jwtProperties.getIssuer())
                // 내용 - 토큰이 발급된 시간(iat): 현재시간
                .setIssuedAt(now)
                // 내용 - 토큰 만료 시간(exp): expiry 멤버 변수값
                .setExpiration(expiry)
                // 내용 - 토큰 대상자(aud): 사용자 학번
                .setAudience(member.getStudentNum())
                // 내용 - 토큰 제목(sub): 사용자 권한
                .setSubject(String.valueOf(member.getRole()))
                // 내용 - 클레임 id: 사용자 id
                .claim("id",member.getMemberId())
                // 서명: 비밀값과 함께 해시값을 HS256 방식으로 암호화
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();
    }

    // JWT 토큰 유효성 검증 메서드
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    // Secret_key 사용해 토큰 복호화
                    .setSigningKey(jwtProperties.getSecretKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e){ // 복호화 과정에서 에러가 나면 유효하지 않은 토큰
            return false;
        }
    }

    // 토큰 기반으로 인증 정보를 가져오는 메서드
    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        Set<SimpleGrantedAuthority> authorities = Collections.singleton(new
                SimpleGrantedAuthority("ROLE_" + claims.getSubject()));

        return new UsernamePasswordAuthenticationToken(new User(claims.getAudience().toString(),"",authorities),"",authorities);
    }

    // 토큰 기반으로 유저 ID를 가져오는 메서드
    public Long getUserId(String token){
        Claims claims = getClaims(token);
        return claims.get("id", Long.class);
    }

    // 클레임을 가져오는 메서드
    private Claims getClaims(String token){
        return Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
