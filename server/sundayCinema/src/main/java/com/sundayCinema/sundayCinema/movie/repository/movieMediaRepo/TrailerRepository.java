package com.sundayCinema.sundayCinema.movie.repository.movieMediaRepo;

import com.sundayCinema.sundayCinema.movie.entity.movieInfo.Movie;
import com.sundayCinema.sundayCinema.movie.entity.movieMedia.Trailer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TrailerRepository extends JpaRepository<Trailer, Long> {
    @Query("SELECT MAX(e.trailerId) FROM Trailer e")
    Long findMaxTrailerId();

    List<Trailer> findByMovie(Movie movie); // 전체 예고편 리스트 가져오기
    boolean existsByMovie(Movie movie);
    Trailer findByMovie_MovieIdAndVodClassContaining(Long movieId, String keyword); // 키워드가 포함된 예고편 가져오기
}
