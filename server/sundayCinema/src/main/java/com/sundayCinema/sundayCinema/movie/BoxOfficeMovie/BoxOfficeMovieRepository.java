package com.sundayCinema.sundayCinema.movie.BoxOfficeMovie;

import com.sundayCinema.sundayCinema.movie.BoxOfficeMovie.BoxOfficeMovie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoxOfficeMovieRepository extends JpaRepository<BoxOfficeMovie, Long> {
}
