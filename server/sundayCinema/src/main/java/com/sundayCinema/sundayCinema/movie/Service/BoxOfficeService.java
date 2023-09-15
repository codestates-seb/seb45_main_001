package com.sundayCinema.sundayCinema.movie.Service;

import com.sundayCinema.sundayCinema.movie.entity.boxOffice.BoxOfficeMovie;
import com.sundayCinema.sundayCinema.movie.entity.boxOffice.ForeignBoxOffice;
import com.sundayCinema.sundayCinema.movie.entity.boxOffice.GenreBoxOffice;
import com.sundayCinema.sundayCinema.movie.entity.boxOffice.KoreaBoxOffice;
import com.sundayCinema.sundayCinema.movie.repository.boxOfficeRepo.BoxOfficeMovieRepository;
import com.sundayCinema.sundayCinema.movie.repository.boxOfficeRepo.ForeignBoxOfficeRepository;
import com.sundayCinema.sundayCinema.movie.repository.boxOfficeRepo.GenreBoxOfficeRepository;
import com.sundayCinema.sundayCinema.movie.repository.boxOfficeRepo.KoreaBoxOfficeRepository;
import com.sundayCinema.sundayCinema.movie.repository.movieInfoRepo.MovieRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BoxOfficeService {
    private final BoxOfficeMovieRepository boxOfficeMovieRepository;
    private final KoreaBoxOfficeRepository koreaBoxOfficeRepository;
    private final ForeignBoxOfficeRepository foreignBoxOfficeRepository;
    private final GenreBoxOfficeRepository genreBoxOfficeRepository;

    private final MovieRepository movieRepository;

    public BoxOfficeService(BoxOfficeMovieRepository boxOfficeMovieRepository, KoreaBoxOfficeRepository koreaBoxOfficeRepository,
                            ForeignBoxOfficeRepository foreignBoxOfficeRepository,
                            GenreBoxOfficeRepository genreBoxOfficeRepository, MovieRepository movieRepository) {
        this.boxOfficeMovieRepository = boxOfficeMovieRepository;
        this.koreaBoxOfficeRepository = koreaBoxOfficeRepository;
        this.foreignBoxOfficeRepository = foreignBoxOfficeRepository;
        this.genreBoxOfficeRepository = genreBoxOfficeRepository;
        this.movieRepository = movieRepository;
    }

    //박스 오피스 타입을 변환해서 저장
    public void saveBoxOffice(String boxofficeCd, BoxOfficeMovie boxOfficeMovie){
        if(boxofficeCd.equals("")){
            BoxOfficeMovie newBoxOfficeMovie = new BoxOfficeMovie(boxOfficeMovie);
            boxOfficeMovieRepository.save(newBoxOfficeMovie);
        }
        else if(boxofficeCd.equals("K")){
            koreaBoxOfficeRepository.save(createKoreaBoxOffice(boxOfficeMovie));
        }
        else if(boxofficeCd.equals("F")){
            foreignBoxOfficeRepository.save(createForeignBoxOffice(boxOfficeMovie));
        }
        else if(boxofficeCd.equals("G")){
            genreBoxOfficeRepository.save(createGenreBoxOffice(boxOfficeMovie));
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
    private GenreBoxOffice createGenreBoxOffice(BoxOfficeMovie boxOfficeMovie) {
        GenreBoxOffice genreBoxOffice = new GenreBoxOffice(boxOfficeMovie);
        return genreBoxOffice;
    }
}
