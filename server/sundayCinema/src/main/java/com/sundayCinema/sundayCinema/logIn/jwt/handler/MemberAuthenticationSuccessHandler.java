package com.sundayCinema.sundayCinema.logIn.jwt.handler;


import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
public class MemberAuthenticationSuccessHandler implements AuthenticationSuccessHandler {  // (1)


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        log.info("request Authorization 값 " + request.getHeader("Authorization"));
        log.info("response Authorization 값 " + response.getHeader("Authorization"));
        log.info("request userProfile 값 " + request.getHeader("userProfile"));
        log.info("response userProfile 값 " + response.getHeader("userProfile"));
    }
}