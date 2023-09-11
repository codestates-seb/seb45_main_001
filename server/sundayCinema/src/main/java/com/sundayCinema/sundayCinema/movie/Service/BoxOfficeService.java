package com.sundayCinema.sundayCinema.movie.Service;

import com.sundayCinema.sundayCinema.movie.entity.boxOffice.BoxOfficeMovie;
import com.sundayCinema.sundayCinema.movie.entity.boxOffice.ForeignBoxOffice;
import com.sundayCinema.sundayCinema.movie.entity.boxOffice.KoreaBoxOffice;
import com.sundayCinema.sundayCinema.movie.repository.boxOfficeRepo.BoxOfficeMovieRepository;
import com.sundayCinema.sundayCinema.movie.repository.boxOfficeRepo.ForeignBoxOfficeRepository;
import com.sundayCinema.sundayCinema.movie.repository.boxOfficeRepo.KoreaBoxOfficeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BoxOfficeService {
    private final BoxOfficeMovieRepository boxOfficeMovieRepository;
    private final KoreaBoxOfficeRepository koreaBoxOfficeRepository;
    private final ForeignBoxOfficeRepository foreignBoxOfficeRepository;

    public BoxOfficeService(BoxOfficeMovieRepository boxOfficeMovieRepository, KoreaBoxOfficeRepository koreaBoxOfficeRepository,
                            ForeignBoxOfficeRepository foreignBoxOfficeRepository) {
        this.boxOfficeMovieRepository = boxOfficeMovieRepository;
        this.koreaBoxOfficeRepository = koreaBoxOfficeRepository;
        this.foreignBoxOfficeRepository = foreignBoxOfficeRepository;
    }

    public void saveBoxOffice(String repNationCd , BoxOfficeMovie boxOfficeMovie){
        if(repNationCd.equals("")){
            log.info("boxOfficeMovie 팩토리 버전:"+boxOfficeMovie.getBoxOfficeId());
            boxOfficeMovieRepository.save(boxOfficeMovie);
        }
        else if(repNationCd.equals("K")){
            koreaBoxOfficeRepository.save(createKoreaBoxOffice(boxOfficeMovie));
        }
        else if(repNationCd.equals("F")){
            foreignBoxOfficeRepository.save(createForeignBoxOffice(boxOfficeMovie));
        }
    }
    ///git
    private KoreaBoxOffice createKoreaBoxOffice(BoxOfficeMovie boxOfficeMovie) {
        KoreaBoxOffice koreaBoxOffice = new KoreaBoxOffice(boxOfficeMovie);
        return koreaBoxOffice;
    }

    private ForeignBoxOffice createForeignBoxOffice(BoxOfficeMovie boxOfficeMovie) {
        ForeignBoxOffice foreignBoxOffice = new ForeignBoxOffice(boxOfficeMovie);
        return foreignBoxOffice;
    }
}
