package com.meltingpot.baeullimflower.global.config;

import com.meltingpot.baeullimflower.global.jwt.TokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // 요청 헤더의 Authorization 키 값 조회
        String authorizationHeader = request.getHeader("Authorization");
        // 가져온 값에서 Bearer 제거
        String token = getAccessToken(authorizationHeader);
        // 가져온 토큰이 유효한지 확인하고, 유효하다면 인증 정보 설정
        if(tokenProvider.validateToken(token)){
            Authentication authentication = tokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);

    }

    private String getAccessToken(String authorizationHeader){
        // Token이 null이 아니고 Bearer로 시작해야지 정상적인 Token임
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            // Bearer 제거 후 리턴
            return authorizationHeader.substring("Bearer ".length());
        }
        return null;
    }
}
