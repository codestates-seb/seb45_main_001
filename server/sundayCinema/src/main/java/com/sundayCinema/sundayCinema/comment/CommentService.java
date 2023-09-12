package com.sundayCinema.sundayCinema.comment;

import com.sundayCinema.sundayCinema.error.BusinessLogicException;
import com.sundayCinema.sundayCinema.security.security.AuthenticationService;

import javax.servlet.http.HttpServletRequest;

public class CommentService {

    private final AuthenticationService authenticationService;

    public CommentService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

/*
1. 사용자 로그인 한 상태
2. 댓글 작성했어요(오펜하이머 상세 페이지)
3. (서버 입장)
    ㄱ. 얘가 인증된 사용자인가?
 */
    public void create(HttpServletRequest request){
        if(authenticationService.loginCheck(request)!="인증되지 않은 사용자입니다"){

        }
    }
}
