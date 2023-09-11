package com.sundayCinema.sundayCinema.comment;

import com.sundayCinema.sundayCinema.error.BusinessLogicException;
import com.sundayCinema.sundayCinema.security.security.AuthenticationService;

import javax.servlet.http.HttpServletRequest;

public class CommentService {

    private final AuthenticationService authenticationService;

    public CommentService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }


    public void create(HttpServletRequest request){
        if(authenticationService.loginCheck(request)!="인증되지 않은 사용자입니다"){

        }
    }
}
