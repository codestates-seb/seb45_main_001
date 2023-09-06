package com.sundayCinema.sundayCinema.movie.repository.boxOfficeRepo;

import com.sundayCinema.sundayCinema.movie.entity.boxofficeMovie.BoxOfficeMovie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoxOfficeMovieRepository extends JpaRepository<BoxOfficeMovie, Long> {
    BoxOfficeMovie findByMovieCd(String MovieCd);
}
