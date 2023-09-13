package com.sundayCinema.sundayCinema.member.service;


import com.sundayCinema.sundayCinema.error.BusinessLogicException;
import com.sundayCinema.sundayCinema.error.ExceptionCode;
import com.sundayCinema.sundayCinema.member.entity.Member;
import com.sundayCinema.sundayCinema.member.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
    private MemberRepository memberRepository;


    private final PasswordEncoder passwordEncoder;

    // 생성자 DI용 파라미터 추가
    public MemberService(MemberRepository memberRepository,
                         PasswordEncoder passwordEncoder)
    {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;

    }

//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    public Member createMember(Member member) {

        verifyExistsEamil(member.getEmail());

        // Password 암호화 (seongwon)
        String encryptedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encryptedPassword);

        // DB에 User Role 저장 (seongwon)


        return memberRepository.save(member);
    }

    //    public Member updateMember(Member member) {
//
//        Member findMember = findVerifiedMember(member.getMemberId());
//
//        Optional.ofNullable(member.getUsername())
//                .ifPresent(username -> findMember.setUsername(username));
//        Optional.ofNullable(member.getPassword())
//                .ifPresent(password -> findMember.setPassword(password));
//
//        return memberRepository.save(findMember);
//    }
    public Member findMember(long memberId) {
        return findVerifiedMember(memberId);
    }

    public List<Member> findMembers() {
        return (List<Member>) memberRepository.findAll();
    }

    public Member findVerifiedMember(long memberId) {
        Optional<Member> optionalMember =
                memberRepository.findById(memberId);
        Member findMember =
                optionalMember.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND, "Member not found with the specified ID"));
        return findMember;
    }

    private void verifyExistsEamil(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        if (member.isPresent())
            throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS, "Member with this email already exists");
    }

}