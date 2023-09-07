package com.sundayCinema.sundayCinema.movie.repository;

import com.sundayCinema.sundayCinema.movie.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    Movie findByMovieId(long movieId);
    Movie findByMovieCd(String movieCd);

    Movie findByMovieNm(String movieNm);

    default boolean existsByMovieCd(String movieCd) {
        return findByMovieCd(movieCd) != null;
    }
}
