package com.meltingpot.baeullimflower.global.config;

import com.meltingpot.baeullimflower.global.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@RequiredArgsConstructor
@Configuration
public class WebSecurityConfig {
    //스프링 시큐리티 기능 비활성화
    @Bean
    public WebSecurityCustomizer configure(){
        return (web -> web.ignoring()
                .requestMatchers("/static/**"));
    }

    //특정 HTTP 요청에 대한 웹 기반 보안 구성
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity, TokenProvider tokenProvider) throws Exception {
        return httpSecurity
                // ui가 아닌 token 인증을 할 것이므로 비활성화
                .httpBasic(AbstractHttpConfigurer::disable)
                // csrf 비활성화
                .csrf(AbstractHttpConfigurer::disable)
                // cors 비활성화
                .cors(AbstractHttpConfigurer::disable)
                //인증 인가 설정
                .authorizeHttpRequests(requests -> {
                    // 회원가입, 로그인은 항상 접근 가능
                    requests.requestMatchers("members/signup", "/members/login").permitAll();
//                    requests.requestMatchers(HttpMethod.POST).authenticated();
                    // 다른 모든 요청은 막기
                    requests.anyRequest().authenticated();
                })
                // jwt를 사용하므로 sateless
                .sessionManagement(
                        sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(new TokenAuthenticationFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    //패스워드 인코더로 사용할 빈 등록
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
