package com.sundayCinema.sundayCinema.member.controller;

import com.sundayCinema.sundayCinema.member.MemberDto.MemberLoginDto;
import com.sundayCinema.sundayCinema.member.MemberDto.MemberPostDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class MemberController {

//- Response header
// * 200 OK
//- Response Body
// * {
//   "message": "회원가입이 성공적으로 완료되었습니다."
//   }
    @PostMapping("/members/signup")
    public ResponseEntity PostMember(@RequestBody @Valid MemberPostDto memberPostDto){
//        {
//            "nickname": "kimcoding",
//                "email": "qwe123@example.com",
//                "password": "secret123!!"
//        }
        return new ResponseEntity<>("회원가입이 성공적으로 완료되었습니다.", HttpStatus.OK);
    }


//2. Post 로그인
//- url(end point) : host/signin
//- 설명 : 로그인 로직입니다.
//- Resquest header
// X
//- Request Body
// * {
//    "email": "qwe123@example.com",
//    "password": "secret123!!"
//   }
//- Response header
// * "Set Cookie" : "JSESSIONID : sessionId"(변경 가능성)
//- Response Body
// * {
//   "message": "로그인이 성공적으로 완료되었습니다."
//   }
//
//(회원 탈퇴, 회원 정보 수정, 회원 단일 조회) -> 추후 마이페이지 구현시 필요하므로 현재는 X
@PostMapping("/members/signin")
public ResponseEntity LoginMember(@RequestBody @Valid MemberLoginDto memberLoginDto){

    return new ResponseEntity<>("로그인이 성공적으로 완료되었습니다.", HttpStatus.OK);
}

}
