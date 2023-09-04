package com.sundayCinema.sundayCinema.movie.repository;

import com.sundayCinema.sundayCinema.movie.entity.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DirectorRepository extends JpaRepository<Director,Long> {
    @Query("SELECT MAX(e.directorId) FROM Director e")
    Long findMaxDirectorId();
}
