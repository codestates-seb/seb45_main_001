package com.sundayCinema.sundayCinema.movie.repository.movieMediaRepo;

import com.sundayCinema.sundayCinema.movie.entity.movieInfo.Movie;
import com.sundayCinema.sundayCinema.movie.entity.movieMedia.BackDrop;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BackDropRepository extends JpaRepository<BackDrop, Long> {
    @Query("SELECT MAX(e.backDropId) FROM BackDrop e")
    Long findMaxBackDropId();
    boolean existsByMovie(Movie movie);
}
