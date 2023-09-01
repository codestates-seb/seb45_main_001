package com.sundayCinema.sundayCinema.movie.repository;

import com.sundayCinema.sundayCinema.movie.entity.BoxOfficeMovie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoxOfficeMovieRepository extends JpaRepository<BoxOfficeMovie, Long> {
}
