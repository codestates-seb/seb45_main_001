package com.sundayCinema.sundayCinema.comment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findByMovieMovieId(Long movieId, Pageable pageable);
    List<Comment> findByMovieMovieId(Long movieId);

}
