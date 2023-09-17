package com.sundayCinema.sundayCinema.movie.repository.movieInfoRepo;

import com.sundayCinema.sundayCinema.movie.entity.movieInfo.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    @Query("SELECT MAX(e.genreId) FROM Genre e")
    Long findMaxGenreId();
}
