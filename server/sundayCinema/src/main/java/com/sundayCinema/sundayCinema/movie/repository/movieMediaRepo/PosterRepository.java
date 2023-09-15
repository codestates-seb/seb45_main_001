package com.sundayCinema.sundayCinema.movie.repository.movieMediaRepo;

import com.sundayCinema.sundayCinema.movie.entity.movieInfo.Movie;
import com.sundayCinema.sundayCinema.movie.entity.movieMedia.Poster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PosterRepository extends JpaRepository<Poster, Long> {
    @Query("SELECT MAX(e.posterId) FROM Poster e")
    Long findMaxPosterId();

    List<Poster> findByMovie(Movie movie); // 전체 리스트 가져오기
    boolean existsByMovie(Movie movie);
}
