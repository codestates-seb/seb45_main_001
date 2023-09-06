package com.sundayCinema.sundayCinema.movie.repository.boxOfficeRepo;

import com.sundayCinema.sundayCinema.movie.entity.boxofficeMovie.BoxOfficeMovie;
import com.sundayCinema.sundayCinema.movie.entity.boxofficeMovie.ForeignBoxOffice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForeignBoxOfficeRepository extends JpaRepository<ForeignBoxOffice, Long> {
    ForeignBoxOffice findByMovieCd(String MovieCd);
}
