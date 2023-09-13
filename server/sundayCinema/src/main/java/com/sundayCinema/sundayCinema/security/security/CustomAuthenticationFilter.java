package com.sundayCinema.sundayCinema.security.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sundayCinema.sundayCinema.security.dto.LoginRequest;
import lombok.extern.slf4j.Slf4j;

import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Order(1)
public class CustomAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final ObjectMapper objectMapper;

    public CustomAuthenticationFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager, ObjectMapper objectMapper) {
        super(defaultFilterProcessesUrl);
        setAuthenticationManager(authenticationManager);
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        if (request.getContentType() == null || !request.getContentType().contains(MediaType.APPLICATION_JSON_VALUE)) {
            throw new AuthenticationServiceException("Content Type is not application/json");
        }

        try {
            // JSON 데이터를 파싱하여 LoginRequest 객체로 변환
            LoginRequest loginRequest = objectMapper.readValue(request.getReader(), LoginRequest.class);

            String email = loginRequest.getEmail();
            String password = loginRequest.getPassword();

            email = (email != null) ? email : "";
            email = email.trim();
            password = (password != null) ? password : "";

            // CustomAuthenticationToken을 생성하여 사용자 인증을 시도합니다.
            CustomAuthenticationToken authRequest = new CustomAuthenticationToken(email, password);

            // 여기서 세션 아이디를 생성하고 쿠키에 설정합니다.
            String sessionId = generateSessionId();
            Cookie sessionCookie = new Cookie("sessionId", sessionId);
            sessionCookie.setPath("/"); // 쿠키의 경로 설정 (루트 경로에 대해 모든 요청에 적용)

            // 쿠키를 응답에 추가
            response.addCookie(sessionCookie);

            return this.getAuthenticationManager().authenticate(authRequest);
        } catch (IOException e) {
            throw new AuthenticationServiceException("Failed to parse JSON request", e);
        }
    }


    // 세션 아이디를 생성하는 메서드
    private String generateSessionId() {
        return UUID.randomUUID().toString(); // 임의의 세션 아이디 생성
    }

}