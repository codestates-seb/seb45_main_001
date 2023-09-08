package com.sundayCinema.sundayCinema.member.MemberDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class MemberPostDto {
    @NotBlank
    @Email
    private String email;
    @NotBlank(message = "이름은 공백이 아니어야 합니다.")
    private String nickname;
    @NotBlank(message = "비밀번호는 영어에 숫자 특수문자를 포함시켜주세요")
    private String password;
}
