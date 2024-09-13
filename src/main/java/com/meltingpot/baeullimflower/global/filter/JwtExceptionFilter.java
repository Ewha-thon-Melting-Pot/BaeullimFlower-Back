package com.meltingpot.baeullimflower.global.filter;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws ServletException, IOException {
        try {
            chain.doFilter(req, res);
        } catch (JwtException ex) {
            setErrorResponse(HttpStatus.UNAUTHORIZED, res, "INVALID_TOKEN", ex.getMessage());
        }
    }

    public void setErrorResponse(HttpStatus status, HttpServletResponse res, String code, String message) throws IOException {
        res.setStatus(status.value());
        res.setContentType("application/json; charset=UTF-8");

        JwtExceptionResponse jwtExceptionResponse = new JwtExceptionResponse(status, code, message);
        res.getWriter().write(jwtExceptionResponse.convertToJson());
    }
}
