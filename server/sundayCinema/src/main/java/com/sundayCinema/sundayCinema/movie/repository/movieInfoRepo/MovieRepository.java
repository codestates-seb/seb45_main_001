package com.sundayCinema.sundayCinema.movie.repository.movieInfoRepo;

import com.sundayCinema.sundayCinema.movie.entity.movieInfo.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    Movie findByMovieId(long movieId);
    Movie findByMovieCd(String movieCd);

    Movie findByMovieNm(String movieNm);

    List<Movie> findByNationsNationNm(String nationName);
    List<Movie> findByNationsNationNmIsNot(String nationName);
    default boolean existsByMovieCd(String movieCd) {
        return findByMovieCd(movieCd) != null;
    }
}
