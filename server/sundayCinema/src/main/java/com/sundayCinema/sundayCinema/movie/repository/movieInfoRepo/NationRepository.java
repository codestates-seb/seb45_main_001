package com.sundayCinema.sundayCinema.movie.repository.movieInfoRepo;

import com.sundayCinema.sundayCinema.movie.entity.Nation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NationRepository extends JpaRepository<Nation, Long> {
    @Query("SELECT MAX(e.nationId) FROM Nation e")
    Long findMaxNationId();
}
