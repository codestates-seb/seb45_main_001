package com.sundayCinema.sundayCinema.movie.api.KOBIS;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sundayCinema.sundayCinema.movie.entity.Actor;
import com.sundayCinema.sundayCinema.movie.entity.BoxOfficeMovie;
import com.sundayCinema.sundayCinema.movie.entity.Movie;
import com.sundayCinema.sundayCinema.movie.repository.ActorRepository;
import com.sundayCinema.sundayCinema.movie.repository.BoxOfficeMovieRepository;
import com.sundayCinema.sundayCinema.movie.repository.MovieRepository;
import kr.or.kobis.kobisopenapi.consumer.rest.KobisOpenAPIRestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
public class KobisService {
    private final String API_KEY = "dd93d4428c56b7a47d27bf57dc81e481"; // 발급받은 API 키 값을 입력해주세요

    private final BoxOfficeMovieRepository boxOfficeMovieRepository;

    private final MovieRepository movieRepository;
    private final ActorRepository actorRepository;

    public KobisService(BoxOfficeMovieRepository boxOfficeMovieRepository, MovieRepository movieRepository, ActorRepository actorRepository) {
        this.boxOfficeMovieRepository = boxOfficeMovieRepository;
        this.movieRepository = movieRepository;
        this.actorRepository = actorRepository;
    }

    public void dailyBoxOffice() throws Exception {

        String dailyResponse = "";
        String movieResponse = "";
        //전날 박스오피스 조회 ( 오늘 날짜꺼는 안나옴.. )
        LocalDateTime time = LocalDateTime.now().minusDays(1);
        String targetDt = time.format(DateTimeFormatter.ofPattern("yyyMMdd"));

        //ROW 개수
        String itemPerPage = "10";

        //다양성영화(Y)/상업영화(N)/전체(default)
        String multiMovieYn = "";

        //한국영화(K)/외국영화(F)/전체(default)
        String repNationCd = "";

        //상영지역별 코드/전체(default)
        String wideAreaCd = "";
        String[] movieTypeCd = new String[0];


        // KOBIS 오픈 API Rest Client를 통해 호출
        KobisOpenAPIRestService service = new KobisOpenAPIRestService(API_KEY);

        // 일일 박스오피스 서비스 호출 (boolean isJson, String targetDt, String itemPerPage,String multiMovieYn, String repNationCd, String wideAreaCd)
        dailyResponse = service.getDailyBoxOffice(true, targetDt, itemPerPage, multiMovieYn, repNationCd, wideAreaCd);

        BoxofficeResponse parsingResponse = parsingKobis(dailyResponse);
        List<BoxOfficeMovie> dailyList = parsingResponse.getBoxOfficeResult().getDailyBoxOfficeList();

        for(int i=0; i< dailyList.size(); i++) {
            BoxOfficeMovie boxOfficeMovie = parsingResponse.getBoxOfficeResult().getDailyBoxOfficeList().get(i);
            boxOfficeMovie.setBoxOfficeId(i);
            movieResponse = service.getMovieInfo(true,boxOfficeMovie.getMovieCd());
            MovieResponse parsingMovieInfo = parsingMovieInfo(movieResponse);
            Movie movie = parsingMovieInfo.getMovieInfoResult().getMovieInfo();
            boxOfficeMovieRepository.save(boxOfficeMovie);
            movieRepository.save(movie);
            if(movie.getActors() != null && !movie.getActors().isEmpty()) {
                for (int j = 0; j < movie.getActors().size(); j++) {
                    Actor actor = movie.getActors().get(j);
                    actor.setActorId(i + j); // 각 배우에 고유한 ID를 부여 (선택적)
                    actorRepository.save(actor);
                }
            }
        }
    }

    public BoxofficeResponse parsingKobis(String response) throws JsonProcessingException {
        String jsonString = response;

        ObjectMapper objectMapper = new ObjectMapper();
        BoxofficeResponse movieData = objectMapper.readValue(jsonString, BoxofficeResponse.class);

        return movieData;
    }
    public MovieResponse parsingMovieInfo(String movieResponse) throws JsonProcessingException {
        String jsonString = movieResponse;

        ObjectMapper objectMapper = new ObjectMapper();
        MovieResponse movieData = objectMapper.readValue(jsonString, MovieResponse.class);

        return movieData;
    }
}
