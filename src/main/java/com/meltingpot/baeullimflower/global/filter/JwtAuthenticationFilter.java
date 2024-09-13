package com.meltingpot.baeullimflower.global.filter;

import com.meltingpot.baeullimflower.global.jwt.JwtAuthenticationProvider;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtAuthenticationProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // 요청 헤더의 Authorization 키 값 조회
        String authorizationHeader = request.getHeader("Authorization");

        // 가져온 값에서 Bearer 제거
        String token = getAccessToken(authorizationHeader);

        if(!ObjectUtils.isEmpty(token)){
            // 토큰이 유효한 경우 인증정보 설정
            if(jwtProvider.isTokenValid(token)){
                Authentication authentication = jwtProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }

    private String getAccessToken(String authorizationHeader){
        /* Token이 null이 아니고 Bearer로 시작해야지 정상적인 Token임 */
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            /* 정상적인 토큰이라면 앞에 Bearer 제거 후 리턴 */
            return authorizationHeader.substring("Bearer ".length());
        }
        return null;
    }
}
