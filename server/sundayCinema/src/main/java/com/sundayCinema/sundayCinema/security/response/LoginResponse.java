package com.sundayCinema.sundayCinema.security.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    private Boolean success;
    private String message;
    private String nickname;
    private String sessionId; // 세션 아이디

    public LoginResponse(Boolean success, String message,String nickname, String sessionId) {
        this.success = success;
        this.message = message;
        this.nickname = nickname;
        this.sessionId = sessionId;
    }
}

