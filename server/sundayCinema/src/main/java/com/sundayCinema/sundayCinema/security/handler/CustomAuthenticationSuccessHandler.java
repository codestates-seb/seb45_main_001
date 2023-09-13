package com.sundayCinema.sundayCinema.security.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 로그인 성공 시 쿠키 생성 및 설정
        Cookie cookie = new Cookie("yourCookieName", "yourCookieValue");
        cookie.setPath("/"); // 쿠키 경로 설정
        cookie.setHttpOnly(true); // JavaScript에서 쿠키에 접근 불가능하도록 설정
        cookie.setMaxAge(60 * 60 * 24); // 쿠키 유효기간 설정 (초 단위, 여기서는 1일로 설정)

        response.addCookie(cookie); // 쿠키를 응답 헤더에 추가
        response.sendRedirect("/"); // 로그인 후 이동할 페이지 지정 (원하는 페이지로 변경)
    }
}
