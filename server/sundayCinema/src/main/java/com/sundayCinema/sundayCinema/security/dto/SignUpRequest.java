package com.sundayCinema.sundayCinema.security.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequest {
    private String nickname;
    private String email;
    private String password;
}