package com.sundayCinema.sundayCinema.logIn.jwt.userDetails;

import lombok.Getter;
import lombok.Setter;


import java.util.List;
@Getter
@Setter
public class CustomUserDetails {
    private String userId;
    private String email;
    private List<String> roles;
}
