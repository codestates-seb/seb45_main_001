package com.sundayCinema.sundayCinema.movie.repository.movieMediaRepo;

import com.sundayCinema.sundayCinema.movie.entity.movieInfo.Movie;
import com.sundayCinema.sundayCinema.movie.entity.movieMedia.YoutubeReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface YoutubeReviewRepository extends JpaRepository<YoutubeReview, Long> {
    @Query("SELECT MAX(e.reviewId) FROM YoutubeReview e")
    Long findMaxReviewId();
    boolean existsByMovie(Movie movie);
}
