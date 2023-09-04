package com.sundayCinema.sundayCinema.movie.repository;

import com.sundayCinema.sundayCinema.movie.entity.BoxOfficeMovie;
import com.sundayCinema.sundayCinema.movie.entity.YoutubeReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface YoutubeReviewRepository extends JpaRepository<YoutubeReview, Long> {
    @Query("SELECT MAX(e.reviewId) FROM YoutubeReview e")
    Long findMaxReviewId();
}
