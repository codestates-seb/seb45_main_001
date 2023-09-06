package com.sundayCinema.sundayCinema.movie.api.KOBIS;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sundayCinema.sundayCinema.movie.entity.*;
import com.sundayCinema.sundayCinema.movie.repository.*;
import kr.or.kobis.kobisopenapi.consumer.rest.KobisOpenAPIRestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
public class KobisService {
    @Value("${KOBIS_API_KEY}")
    private String KobisApiKEY; // 발급받은 API 키 값을 입력해주세요

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
    String repNationCd_Korean = "K";
    String repNationCd_Foreign = "F";

    //상영지역별 코드/전체(default)
    String wideAreaCd = "";



    private final MovieRepository movieRepository;
    private final ActorRepository actorRepository;
    private final DirectorRepository directorRepository;
    private final MovieAuditRepository movieAuditRepository;
    private final NationRepository nationRepository;
    private final GenreRepository genreRepository;

    private final BoxOfficeFactory boxOfficeFactory;

    public KobisService(MovieRepository movieRepository, ActorRepository actorRepository,
                        DirectorRepository directorRepository, MovieAuditRepository movieAuditRepository,
                        NationRepository nationRepository, GenreRepository genreRepository,
                        BoxOfficeFactory boxOfficeFactory) {
        this.movieRepository = movieRepository;
        this.actorRepository = actorRepository;
        this.directorRepository = directorRepository;
        this.movieAuditRepository = movieAuditRepository;
        this.nationRepository = nationRepository;
        this.genreRepository = genreRepository;
        this.boxOfficeFactory = boxOfficeFactory;
    }

    public void dailyUpdateBoxOffice(String repNationCd) throws Exception {

        // KOBIS 오픈 API Rest Client를 통해 호출
        KobisOpenAPIRestService service = new KobisOpenAPIRestService(KobisApiKEY);

        // 일일 박스오피스 서비스 호출 (boolean isJson, String targetDt, String itemPerPage,String multiMovieYn, String repNationCd, String wideAreaCd)
        dailyResponse = service.getDailyBoxOffice(true, targetDt, itemPerPage, multiMovieYn, repNationCd, wideAreaCd);

        BoxofficeResponse parsingResponse = parsingKobis(dailyResponse);
        List<BoxOfficeMovie> dailyList = parsingResponse.getBoxOfficeResult().getDailyBoxOfficeList();

        saveKobis(service, dailyList, parsingResponse, repNationCd);
    }


    public void saveKobis(KobisOpenAPIRestService service, List<BoxOfficeMovie> dailyList,
                              BoxofficeResponse parsingResponse, String repNationCd) throws Exception {
        for (int i = 0; i < dailyList.size(); i++) {
            BoxOfficeMovie boxOfficeMovie = parsingResponse.getBoxOfficeResult().getDailyBoxOfficeList().get(i);
            boxOfficeMovie.setBoxOfficeId(i);
            movieResponse = service.getMovieInfo(true, boxOfficeMovie.getMovieCd());
            MovieResponse parsingMovieInfo = parsingMovieInfo(movieResponse);
            Movie movie = parsingMovieInfo.getMovieInfoResult().getMovieInfo();
            boxOfficeFactory.saveBoxOffice(repNationCd,boxOfficeMovie);

            if(verifyExistMovie(boxOfficeMovie.getMovieCd())) continue;
            else {
                movieRepository.save(movie);
                saveActors(movie);
                saveGenres(movie);
                saveNations(movie);
                saveMovieAudit(movie);
                saveDirectors(movie);
            }
        }
    }


    public boolean verifyExistMovie(String movieCd){
        boolean movieExists = movieRepository.existsByMovieCd(movieCd);

        return movieExists;
    }

    public void saveDirectors(Movie movie) {
        List<Director> directors = movie.getDirectors();

        if (directors != null && !directors.isEmpty()) {
            Long maxDirectorId = directorRepository.findMaxDirectorId();

            for (int i = 0; i < directors.size(); i++) {
                Director director = directors.get(i);
                director.setMovie(movie);

                if (maxDirectorId != null) {
                    director.setDirectorId(maxDirectorId + i + 1);
                } else {
                    director.setDirectorId(i);
                }

                directorRepository.save(director);
            }
        }
    }
    public void saveGenres(Movie movie) {
        for (int i = 0; i < movie.getGenres().size(); i++) {
            if (movie.getGenres() != null && !movie.getGenres().isEmpty()) {
                Genre genre = movie.getGenres().get(i);
                genre.setMovie(movie);
                if (genreRepository.findMaxGenreId() == null) {
                    genre.setGenreId(i);
                    genreRepository.save(genre);
                } else {
                    long genreId = genreRepository.findMaxGenreId();
                    genre.setGenreId(genreId + 1); // 각 배우에 고유한 ID를 부여 (선택적)
                    genreRepository.save(genre);
                }
            }
        }
    }

    public void saveNations(Movie movie) {
        for (int i = 0; i < movie.getNations().size(); i++) {
            if (movie.getNations().get(i) != null && !movie.getNations().isEmpty()) {
                Nation nation = movie.getNations().get(i);
                nation.setMovie(movie);
                if (nationRepository.findMaxNationId() == null) {
                    nation.setNationId(i);
                    nationRepository.save(nation);
                } else {
                    long nationId = nationRepository.findMaxNationId();
                    nation.setNationId(nationId + 1); // 각 배우에 고유한 ID를 부여 (선택적)
                    nationRepository.save(nation);
                }
            }
        }
    }

    public void saveMovieAudit(Movie movie) {
        for (int i = 0; i < movie.getAudits().size(); i++) {
            if (movie.getAudits() != null && !movie.getAudits().isEmpty()) {
                MovieAudit audit = movie.getAudits().get(i);
                audit.setMovie(movie);
                if (movieAuditRepository.findMaxAuditId() == null) {
                    audit.setAuditId(i);
                    movieAuditRepository.save(audit);
                } else {
                    long auditId = movieAuditRepository.findMaxAuditId();
                    audit.setAuditId(auditId + 1); // 각 배우에 고유한 ID를 부여 (선택적)
                    movieAuditRepository.save(audit);
                }
            }
        }
    }

    public void saveActors(Movie movie) {
        List<Actor> actors = movie.getActors();
        int maxActorsToSave = Math.min(actors.size(), 4); // 최대 4명까지만 저장

        if (maxActorsToSave > 0) {
            Long maxActorId = actorRepository.findMaxActorId();

            for (int i = 0; i < maxActorsToSave; i++) {
                Actor actor = actors.get(i);
                actor.setMovie(movie);

                if (maxActorId != null) {
                    actor.setActorId(maxActorId + i + 1);
                } else {
                    actor.setActorId(i);
                }

                actorRepository.save(actor);
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
