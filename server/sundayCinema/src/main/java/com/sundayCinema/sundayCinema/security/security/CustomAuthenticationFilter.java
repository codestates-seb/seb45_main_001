package com.sundayCinema.sundayCinema.security.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sundayCinema.sundayCinema.security.dto.LoginRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        log.info("CustomAuthenticationFilter's attemptAuthentication method is called.");
        if (request.getContentType() != null && request.getContentType().contains("application/json")) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                LoginRequest authenticationRequest = objectMapper.readValue(
                        request.getInputStream(), LoginRequest.class);

                String username = authenticationRequest.getEmail();
                String password = authenticationRequest.getPassword();
                // 추가: 디버깅 로그
                log.info("Attempting authentication for username: " + username);
                UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username,
                        password);
                log.info("확인 ;"+authRequest.getName());
                setDetails(request, authRequest);

                return this.getAuthenticationManager().authenticate(authRequest);
            } catch (IOException e) {
                log.error("JSON 파싱 오류: " + e.getMessage(), e);
                throw new AuthenticationServiceException("Failed to parse JSON authentication request", e);
            }
        } else {
            // 다른 요청 형식은 기본 폼 로그인으로 처리
            return super.attemptAuthentication(request, response);
        }
    }
}