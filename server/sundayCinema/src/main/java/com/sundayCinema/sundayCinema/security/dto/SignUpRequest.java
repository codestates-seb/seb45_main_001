package com.sundayCinema.sundayCinema.security.dto;


import lombok.Getter;
import lombok.Setter;

public class SignUpRequest {
    private String nickname;
    private String email;
    private String password;

    public SignUpRequest() {
        // 기본 생성자
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}