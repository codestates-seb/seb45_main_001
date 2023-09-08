package com.sundayCinema.sundayCinema.security.config;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // 401 Unauthorized 상태 코드를 클라이언트에게 반환
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        // 클라이언트에게 JSON 또는 메시지를 반환할 수 있음
        response.getWriter().write("Authentication required. Please log in.");
    }
}
