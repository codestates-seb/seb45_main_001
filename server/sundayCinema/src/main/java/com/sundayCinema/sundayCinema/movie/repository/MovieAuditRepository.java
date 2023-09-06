package com.sundayCinema.sundayCinema.movie.repository;

import com.sundayCinema.sundayCinema.movie.entity.MovieAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MovieAuditRepository extends JpaRepository<MovieAudit, Long> {
    @Query("SELECT MAX(e.auditId) FROM MovieAudit e")
    Long findMaxAuditId();
}
