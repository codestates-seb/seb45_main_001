package com.sundayCinema.sundayCinema.movie.repository.boxOfficeRepo;

import com.sundayCinema.sundayCinema.movie.entity.boxOffice.KoreaBoxOffice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KoreaBoxOfficeRepository extends JpaRepository<KoreaBoxOffice, Long> {
    void deleteAll();
    KoreaBoxOffice findByMovieCd(String MovieCd);
}
