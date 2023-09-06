package com.sundayCinema.sundayCinema.movie.repository.boxOfficeRepo;

import com.sundayCinema.sundayCinema.movie.entity.boxofficeMovie.BoxOfficeMovie;
import com.sundayCinema.sundayCinema.movie.entity.boxofficeMovie.KoreaBoxOffice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KoreaBoxOfficeRepository extends JpaRepository<KoreaBoxOffice, Long> {
    KoreaBoxOffice findByMovieCd(String MovieCd);
}
