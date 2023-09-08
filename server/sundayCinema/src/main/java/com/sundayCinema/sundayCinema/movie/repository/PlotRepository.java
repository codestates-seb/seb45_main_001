package com.sundayCinema.sundayCinema.movie.repository;

import com.sundayCinema.sundayCinema.movie.entity.Plots;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PlotRepository extends JpaRepository<Plots, Long> {
    @Query("SELECT MAX(e.plotId) FROM Plots e")
    Long findMaxPlotId();
}
