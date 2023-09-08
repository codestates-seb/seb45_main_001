package com.sundayCinema.sundayCinema.security.security;

import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Order(1)
public class CustomAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    public CustomAuthenticationFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager) {
        super(defaultFilterProcessesUrl);
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        // 사용자가 전달한 인증 정보를 읽어옵니다.
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        email = (email != null) ? email : "";
        email = email.trim();
        password = (password != null) ? password : "";

        // CustomAuthenticationToken을 생성하여 사용자 인증을 시도합니다.
        CustomAuthenticationToken authRequest = new CustomAuthenticationToken(email, password);

        return this.getAuthenticationManager().authenticate(authRequest);
    }

}