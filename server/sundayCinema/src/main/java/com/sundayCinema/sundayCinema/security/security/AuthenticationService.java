package com.sundayCinema.sundayCinema.security.security;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
public class AuthenticationService {
    private final HttpServletRequest request;

    public AuthenticationService(HttpServletRequest request) {
        this.request = request;
    }

    public String getMemberEmail() {
        // 현재 사용자의 인증 정보를 가져옵니다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 인증 정보에서 사용자 객체를 가져옵니다.
        if (authentication != null && authentication.isAuthenticated()) {
            // 인증된 사용자의 이메일을 반환합니다.
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetailsImpl) {
                return ((UserDetailsImpl) principal).getUsername(); // 이메일을 반환합니다.
            }
        }
        // 인증되지 않은 경우 또는 사용자 정보를 찾을 수 없는 경우 null을 반환합니다.
        return null;
    }

    public HttpSession getSession() {
        // HttpServletRequest를 사용하여 HttpSession을 가져옵니다.
        return request.getSession();
    }


    public boolean loginCheck(HttpServletRequest request) {
        HttpSession requestSession = request.getSession();
        HttpSession authenticationSession = getSession();
        if (requestSession.equals(authenticationSession)) return true;
        else return false;
    }

    // 현재 이용중인 사용자의 인증 여부 체크용 메서드
}
