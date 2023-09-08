package com.sundayCinema.sundayCinema.member.mapper;


import com.sundayCinema.sundayCinema.member.dto.MemberPostDto;
import com.sundayCinema.sundayCinema.member.dto.MemberResponseDto;
import com.sundayCinema.sundayCinema.member.entity.Member;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberMapper {
    Member memberPostDtoToMember(MemberPostDto memberPostDto);
    MemberResponseDto memberToMemberResponseDto(Member member);
}
