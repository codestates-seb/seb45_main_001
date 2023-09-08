package com.sundayCinema.sundayCinema.member.MemberDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDto {

    //private long memberId;
    private String email;
    private String nickname;
    private String password;

    /*
    public long getMemberId() {
        return memberId;
    }

    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }


     */
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String username) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}