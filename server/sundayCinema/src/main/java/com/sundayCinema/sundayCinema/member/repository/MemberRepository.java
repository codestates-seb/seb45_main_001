package com.sundayCinema.sundayCinema.member.repository;

import com.sundayCinema.sundayCinema.member.entity.Member;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MemberRepository extends CrudRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
}

