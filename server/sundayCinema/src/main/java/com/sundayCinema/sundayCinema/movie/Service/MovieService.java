package com.sundayCinema.sundayCinema.movie.Service;

import com.sundayCinema.sundayCinema.movie.api.KMDB.KdmbService;
import com.sundayCinema.sundayCinema.movie.api.KOBIS.KobisService;
import com.sundayCinema.sundayCinema.movie.api.youtubeAPI.YoutubeService;
import com.sundayCinema.sundayCinema.movie.entity.BoxOfficeMovie;
import com.sundayCinema.sundayCinema.movie.entity.ForeignBoxOffice;
import com.sundayCinema.sundayCinema.movie.entity.KoreaBoxOffice;
import com.sundayCinema.sundayCinema.movie.entity.Movie;
import com.sundayCinema.sundayCinema.movie.entity.Poster;
import com.sundayCinema.sundayCinema.movie.repository.BoxOfficeMovieRepository;
import com.sundayCinema.sundayCinema.movie.repository.ForeignBoxOfficeRepository;
import com.sundayCinema.sundayCinema.movie.repository.KoreaBoxOfficeRepository;
import com.sundayCinema.sundayCinema.movie.repository.MovieRepository;
import com.sundayCinema.sundayCinema.movie.repository.PosterRepository;
import com.sundayCinema.sundayCinema.movie.repository.TrailerRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class MovieService {
    private final TrailerRepository trailerRepository;
    private final MovieRepository movieRepository;
    private final PosterRepository posterRepository;
    private final BoxOfficeMovieRepository boxOfficeMovieRepository;
    private final KoreaBoxOfficeRepository koreaBoxOfficeRepository;
    private final ForeignBoxOfficeRepository foreignBoxOfficeRepository;
    private final KdmbService kdmbService;
    private final KobisService kobisService;
    private final YoutubeService youtubeService;

    public MovieService(TrailerRepository trailerRepository, MovieRepository movieRepository,
                        PosterRepository posterRepository, BoxOfficeMovieRepository boxOfficeMovieRepository,
                        KoreaBoxOfficeRepository koreaBoxOfficeRepository, ForeignBoxOfficeRepository foreignBoxOfficeRepository,
                        KdmbService kdmbService, KobisService kobisService, YoutubeService youtubeService) {
        this.trailerRepository = trailerRepository;
        this.movieRepository = movieRepository;
        this.posterRepository = posterRepository;
        this.boxOfficeMovieRepository = boxOfficeMovieRepository;
        this.koreaBoxOfficeRepository = koreaBoxOfficeRepository;
        this.foreignBoxOfficeRepository = foreignBoxOfficeRepository;
        this.kdmbService = kdmbService;
        this.kobisService = kobisService;
        this.youtubeService = youtubeService;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void dailyUpdateAll() throws Exception {
        kobisService.dailyUpdateBoxOffice("");
        kobisService.dailyUpdateBoxOffice("K");
        kobisService.dailyUpdateBoxOffice("F");
        dailyUpdateMedia();
        getReview();
    }

    public Movie loadMovie(String movieNm) {
        Movie findMovie = movieRepository.findByMovieNm(movieNm);

        return findMovie;
    }

    public List<Poster> loadPosterList(Movie movie) {
        List<Poster> posterList = posterRepository.findByMovie(movie);
        return posterList;
    }

    public List<BoxOfficeMovie> loadBoxOffice() {

        return boxOfficeMovieRepository.findAll();
    }
    public List<KoreaBoxOffice> loadKoreaBoxOffice() {

        return koreaBoxOfficeRepository.findAll();
    }
    public List<ForeignBoxOffice> loadForeignBoxOffice() {

        return foreignBoxOfficeRepository.findAll();
    }
    public void dailyUpdateMedia() throws Exception {

        List<BoxOfficeMovie> boxList = boxOfficeMovieRepository.findAll();
        for (int i = 0; i < boxList.size(); i++) {
            String movieNm = boxList.get(i).getMovieNm();
            String movieCd = boxList.get(i).getMovieCd();
            String openDt = boxList.get(i).getOpenDt();
            String outputDate = parsingDate(openDt);
            kdmbService.generateKdmb(movieCd, movieNm, outputDate);
        }
        List<KoreaBoxOffice> kList = koreaBoxOfficeRepository.findAll();
        for (int i = 0; i < kList.size(); i++) {
            String movieNm = kList.get(i).getMovieNm();
            String movieCd = kList.get(i).getMovieCd();
            String openDt = kList.get(i).getOpenDt();
            String outputDate = parsingDate(openDt);
            kdmbService.generateKdmb(movieCd, movieNm, outputDate);
        }
        List<ForeignBoxOffice> fList = foreignBoxOfficeRepository.findAll();
        for (int i = 0; i < fList.size(); i++) {
            String movieNm = fList.get(i).getMovieNm();
            String movieCd = fList.get(i).getMovieCd();
            String openDt = fList.get(i).getOpenDt();
            String outputDate = parsingDate(openDt);
            kdmbService.generateKdmb(movieCd, movieNm, outputDate);
        }
    }


    public void getReview() throws GeneralSecurityException, IOException {
        List<BoxOfficeMovie> boxList= boxOfficeMovieRepository.findAll();
        List<KoreaBoxOffice> kList = koreaBoxOfficeRepository.findAll();
        List<ForeignBoxOffice> fList = foreignBoxOfficeRepository.findAll();
        for(int i=0; i< boxList.size(); i++) {
            String movieName = boxList.get(i).getMovieNm();
            String searchReview = youtubeService.extractYoutube(youtubeService.searchYoutube(movieName, "리뷰"));
            youtubeService.saveYoutube(searchReview, movieName);
        }
        for(int i=0; i< kList.size(); i++) {
            String movieName = kList.get(i).getMovieNm();
            String searchReview = youtubeService.extractYoutube(youtubeService.searchYoutube(movieName, "리뷰"));
            youtubeService.saveYoutube(searchReview, movieName);
        }
        for(int i=0; i< fList.size(); i++) {
            String movieName = fList.get(i).getMovieNm();
            String searchReview = youtubeService.extractYoutube(youtubeService.searchYoutube(movieName, "리뷰"));
            youtubeService.saveYoutube(searchReview, movieName);
        }
    }

    public String parsingDate(String openDt) throws ParseException {
        if (openDt == null || openDt.trim().isEmpty()) {
            // 빈 문자열 또는 null인 경우에 대한 처리
            return "";
        }
        DateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        // 출력 형식 지정
        DateFormat outputDateFormat = new SimpleDateFormat("yyyyMMdd");
        // 입력 문자열을 Date 객체로 파싱
        Date date = inputDateFormat.parse(openDt);

        // Date 객체를 원하는 출력 형식으로 포맷팅
        String outputDate = outputDateFormat.format(date);

        return outputDate;
    }
}