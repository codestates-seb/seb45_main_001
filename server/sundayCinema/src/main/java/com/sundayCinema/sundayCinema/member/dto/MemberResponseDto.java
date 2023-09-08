package com.sundayCinema.sundayCinema.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberResponseDto {
    private long memberId;

    private String email;

    private String nickname;

    private String password;
}
