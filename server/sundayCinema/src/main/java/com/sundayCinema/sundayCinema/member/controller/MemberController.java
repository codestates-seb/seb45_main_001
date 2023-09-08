//package com.sundayCinema.sundayCinema.member.controller;
//
//
//import com.sundayCinema.sundayCinema.member.MemberDto.MemberPostDto;
//import com.sundayCinema.sundayCinema.member.MemberDto.MemberResponseDto;
//import com.sundayCinema.sundayCinema.member.entity.Member;
//import com.sundayCinema.sundayCinema.member.mapper.MemberMapper;
//import com.sundayCinema.sundayCinema.member.service.MemberService;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//import javax.validation.constraints.Positive;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@RestController
//@RequestMapping("/members")
//@Validated
//public class MemberController {
//
//    // @AutoWired 애너테이션 제거 (seongwon)
//    private final MemberService memberService;
//    private final MemberMapper mapper;
//
//
//    public MemberController(MemberService memberService, MemberMapper mapper) {
//        this.memberService = memberService;
//        this.mapper = mapper;
//    }
//
//    @PostMapping
//    public ResponseEntity postMember(@Valid @RequestBody MemberPostDto memberDto) {
//
//        Member member = mapper.memberPostDtoToMember(memberDto);
//        Member response = memberService.createMember(member);
//
//        return new ResponseEntity<>(mapper.memberToMemberResponseDto(response), HttpStatus.CREATED);
//    }
//
//    @GetMapping("/{member-id}")
//    public ResponseEntity getMember(@PathVariable("member-id") @Positive long memberId) {
//
//        Member response = memberService.findMember(memberId);
//
//        return new ResponseEntity<>(mapper.memberToMemberResponseDto(response), HttpStatus.OK);
//    }
//
//    @GetMapping
//    public ResponseEntity getMembers() {
//
//        List<Member> members = memberService.findMembers();
//
//        List<MemberResponseDto> response =
//                members.stream()
//                        .map(member -> mapper.memberToMemberResponseDto(member))
//                        .collect(Collectors.toList());
//
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
//}
