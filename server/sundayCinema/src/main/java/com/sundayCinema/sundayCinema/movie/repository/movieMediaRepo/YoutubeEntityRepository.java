package com.sundayCinema.sundayCinema.movie.repository.movieMediaRepo;

import com.sundayCinema.sundayCinema.movie.entity.movieMedia.YoutubeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface YoutubeEntityRepository extends JpaRepository<YoutubeEntity, Long> {
    @Query("SELECT MAX(e.youtubeId) FROM YoutubeEntity e")
    Long findMaxYoutubeId();
}
