package com.sundayCinema.sundayCinema.comment;

import com.sundayCinema.sundayCinema.member.Member;
import com.sundayCinema.sundayCinema.movie.entity.movieInfo.Movie;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;


public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByMovieMovieId(long movieId);

    Optional<Comment> findByMovieMovieIdAndMemberMemberId(Long movieId, Long memberId);
    boolean existsByMemberAndMovie(Member member, Movie movie);
}