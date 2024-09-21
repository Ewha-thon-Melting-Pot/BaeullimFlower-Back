package com.meltingpot.baeullimflower.global.jwt;

import com.meltingpot.baeullimflower.member.domain.Member;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class JwtAuthenticationProvider {
    private final JwtProperties jwtProperties;

    public String generateToken(Member member, Duration expiredAt){
        Date now = new Date();
        return makeToken(member, new Date(now.getTime() + expiredAt.toMillis()));
    }

    // 토큰 생성
    public String makeToken(Member member, Date expiredAt){
        Date now = new Date();

        return Jwts.builder()
                // 헤더 - 토큰 타입(typ): JWT
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                // 내용 - 토큰 발급자(iss): properties에서 설정한 값
                .setIssuer(jwtProperties.getIssuer())
                // 내용 - 토큰이 발급된 시간(iat): 현재시간
                .setIssuedAt(now)
                // 내용 - 토큰 만료 시간(exp): expiry 멤버 변수값
                .setExpiration(expiredAt)
                // 내용 - 토큰 제목(sub): 사용자 학번
                .setSubject(member.getStudentNum())
                // 내용 - 클레임 role: 사용자 권한
                .claim("role", member.getRole().toString())
                // 내용 - 클레임 id: 사용자 id
                .claim("id",member.getMemberId())
                // 서명: 비밀값과 함께 해시값을 HS256 방식으로 암호화
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretkey())
                .compact();
    }

    // 토큰 유효성 검증
    public boolean isTokenValid(String token){
        try{
            // secretKey를 사용해 토큰 복호화
            Jwts.parser()
                    .setSigningKey(jwtProperties.getSecretkey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e){
            // 복호화 과정에서 에러가 나면 유효하지 않은 토큰 -> JwtException 던지기
            throw new JwtException("유효하지 않은 토큰입니다.");
        }
    }

    // 토큰 기반으로 인증정보 가져오기
    public Authentication getAuthentication(String token){
        Claims claims = getClaims(token);
        Set<SimpleGrantedAuthority> authorities = Collections.singleton(
                new SimpleGrantedAuthority("ROLE_" + claims.get("role")));
        return new UsernamePasswordAuthenticationToken(new User(claims.getSubject(),"",authorities),"",authorities);
    }

    // 클레임 가져오기
    private Claims getClaims(String token){
        return Jwts.parser()
                .setSigningKey(jwtProperties.getSecretkey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // 토큰 만료 날짜 가져오기
    public Date getExpirationDate(String token){
        return getClaims(token).getExpiration();
    }
}
