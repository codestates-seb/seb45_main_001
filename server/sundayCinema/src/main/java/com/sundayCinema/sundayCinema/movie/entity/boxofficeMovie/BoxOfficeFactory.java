package com.sundayCinema.sundayCinema.movie.entity.boxofficeMovie;

import com.sundayCinema.sundayCinema.movie.repository.boxOfficeRepo.BoxOfficeMovieRepository;
import com.sundayCinema.sundayCinema.movie.repository.boxOfficeRepo.ForeignBoxOfficeRepository;
import com.sundayCinema.sundayCinema.movie.repository.boxOfficeRepo.KoreaBoxOfficeRepository;
import org.springframework.stereotype.Component;

@Component
public class BoxOfficeFactory {
    private final BoxOfficeMovieRepository boxOfficeMovieRepository;
    private final KoreaBoxOfficeRepository koreaBoxOfficeRepository;
    private final ForeignBoxOfficeRepository foreignBoxOfficeRepository;

    public BoxOfficeFactory(BoxOfficeMovieRepository boxOfficeMovieRepository, KoreaBoxOfficeRepository koreaBoxOfficeRepository,
                            ForeignBoxOfficeRepository foreignBoxOfficeRepository) {
        this.boxOfficeMovieRepository = boxOfficeMovieRepository;
        this.koreaBoxOfficeRepository = koreaBoxOfficeRepository;
        this.foreignBoxOfficeRepository = foreignBoxOfficeRepository;
    }

    public void saveBoxOffice(String repNationCd ,BoxOfficeMovie boxOfficeMovie){
        if(repNationCd.equals("")){
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
