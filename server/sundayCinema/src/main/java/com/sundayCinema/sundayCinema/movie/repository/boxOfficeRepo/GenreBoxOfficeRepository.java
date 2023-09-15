package com.sundayCinema.sundayCinema.movie.repository.boxOfficeRepo;

import com.sundayCinema.sundayCinema.movie.entity.boxOffice.GenreBoxOffice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreBoxOfficeRepository extends JpaRepository<GenreBoxOffice, Long> {
}
