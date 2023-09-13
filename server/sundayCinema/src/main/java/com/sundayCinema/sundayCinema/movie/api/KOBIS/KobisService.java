package com.sundayCinema.sundayCinema.movie.api.KOBIS;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sundayCinema.sundayCinema.movie.Service.BoxOfficeService;
import com.sundayCinema.sundayCinema.movie.api.KMDB.KdmbService;
import com.sundayCinema.sundayCinema.movie.api.TMDB.MovieDetails;
import com.sundayCinema.sundayCinema.movie.api.TMDB.TmdbService;
import com.sundayCinema.sundayCinema.movie.entity.boxOffice.BoxOfficeMovie;
import com.sundayCinema.sundayCinema.movie.entity.movieInfo.*;
import com.sundayCinema.sundayCinema.movie.repository.boxOfficeRepo.BoxOfficeMovieRepository;
import com.sundayCinema.sundayCinema.movie.repository.boxOfficeRepo.ForeignBoxOfficeRepository;
import com.sundayCinema.sundayCinema.movie.repository.boxOfficeRepo.KoreaBoxOfficeRepository;
import com.sundayCinema.sundayCinema.movie.repository.movieInfoRepo.*;
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

    String boxResponse = "";
    String movieResponse = "";
    //전날 박스오피스 조회 ( 오늘 날짜꺼는 안나옴.. )
    LocalDateTime time = LocalDateTime.now().minusDays(1);
    String targetDt = time.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

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

    String[] movieTypeCdArr = new String[0];

    private final MovieRepository movieRepository;
    private final ActorRepository actorRepository;
    private final DirectorRepository directorRepository;
    private final MovieAuditRepository movieAuditRepository;
    private final NationRepository nationRepository;
    private final GenreRepository genreRepository;
    private final BoxOfficeService boxOfficeFactory;

    private final BoxOfficeMovieRepository boxOfficeMovieRepository;
    private final KoreaBoxOfficeRepository koreaBoxOfficeRepository;
    private final ForeignBoxOfficeRepository foreignBoxOfficeRepository;
    private final KdmbService kdmbService;

    private final TmdbService tmdbService;

    public KobisService(MovieRepository movieRepository, ActorRepository actorRepository,
                        DirectorRepository directorRepository, MovieAuditRepository movieAuditRepository,
                        NationRepository nationRepository, GenreRepository genreRepository, BoxOfficeService boxOfficeFactory,
                        BoxOfficeMovieRepository boxOfficeMovieRepository, KoreaBoxOfficeRepository koreaBoxOfficeRepository,
                        ForeignBoxOfficeRepository foreignBoxOfficeRepository,
                        KdmbService kdmbService, TmdbService tmdbService) {
        this.movieRepository = movieRepository;
        this.actorRepository = actorRepository;
        this.directorRepository = directorRepository;
        this.movieAuditRepository = movieAuditRepository;
        this.nationRepository = nationRepository;
        this.genreRepository = genreRepository;
        this.boxOfficeFactory = boxOfficeFactory;
        this.boxOfficeMovieRepository = boxOfficeMovieRepository;
        this.koreaBoxOfficeRepository = koreaBoxOfficeRepository;
        this.foreignBoxOfficeRepository = foreignBoxOfficeRepository;
        this.kdmbService = kdmbService;
        this.tmdbService = tmdbService;
    }

    public List<BoxOfficeMovie> searchingDailyBoxOffice(String repNationCd) throws Exception {
        KobisOpenAPIRestService service = new KobisOpenAPIRestService(KobisApiKEY);

        // 일일 박스오피스 서비스 호출 (boolean isJson, String targetDt, String itemPerPage,String multiMovieYn, String repNationCd, String wideAreaCd)
        boxResponse = service.getDailyBoxOffice(true, targetDt, itemPerPage, multiMovieYn, repNationCd, wideAreaCd);

        BoxofficeResponse parsingResponse = parsingKobis(boxResponse);
        List<BoxOfficeMovie> dailyList = parsingResponse.getBoxOfficeResult().getDailyBoxOfficeList();

        return dailyList;
    }

    public List<BoxOfficeMovie> searchingBoxOffice(String targetDt) throws Exception {
        KobisOpenAPIRestService service = new KobisOpenAPIRestService(KobisApiKEY);

        // 일일 박스오피스 서비스 호출 (boolean isJson, String targetDt, String itemPerPage,String multiMovieYn, String repNationCd, String wideAreaCd)
        boxResponse = service.getDailyBoxOffice(true, targetDt, itemPerPage, multiMovieYn, repNationCd, wideAreaCd);
        log.info(boxResponse.toString());
        BoxofficeResponse parsingResponse = parsingKobis(boxResponse);
        List<BoxOfficeMovie> dailyList = parsingResponse.getBoxOfficeResult().getDailyBoxOfficeList();

        return dailyList;
    }

    public void saveBoxOffice(String targetDt) throws Exception {
        KobisOpenAPIRestService service = new KobisOpenAPIRestService(KobisApiKEY);
        List<BoxOfficeMovie> dailyList= searchingBoxOffice(targetDt);

        for (int i = 0; i < dailyList.size(); i++) {
            BoxOfficeMovie boxOfficeMovie = dailyList.get(i);

            movieResponse = service.getMovieInfo(true, boxOfficeMovie.getMovieCd());
            MovieResponse parsingMovieInfo = parsingMovieInfo(movieResponse);
            Movie movie = parsingMovieInfo.getMovieInfoResult().getMovieInfo();

            if (verifyExistMovie(boxOfficeMovie.getMovieCd())) continue;
            else {
                String movieNm = dailyList.get(i).getMovieNm();
                String movieCd = dailyList.get(i).getMovieCd();
                String outputDate = targetDt;
                MovieDetails movieDetails = tmdbService.getMovieDetailsByTitle(movieNm);
                movie.setBackDrop(movieDetails.getBackdropPath());
                movieRepository.save(movie);
                saveActors(movie);
                saveGenres(movie);
                saveNations(movie);
                saveMovieAudit(movie);
                saveDirectors(movie);
                kdmbService.generateKdmb(movieCd, movieNm, outputDate);
            }
        }
    }

    public void saveKobis() throws Exception {
        KobisOpenAPIRestService service = new KobisOpenAPIRestService(KobisApiKEY);
        boxOfficeMovieRepository.deleteAll();
        koreaBoxOfficeRepository.deleteAll();
        foreignBoxOfficeRepository.deleteAll();
        List<BoxOfficeMovie> dailyList = searchingDailyBoxOffice("");
        List<BoxOfficeMovie> kList = searchingDailyBoxOffice("K");
        List<BoxOfficeMovie> fList = searchingDailyBoxOffice("F");
        for (int i = 0; i < dailyList.size(); i++) {
            BoxOfficeMovie boxOfficeMovie = dailyList.get(i);
            boxOfficeMovie.setBoxOfficeId(i);
            log.info("setBoxOfficeId: "+boxOfficeMovie.getBoxOfficeId());
            movieResponse = service.getMovieInfo(true, boxOfficeMovie.getMovieCd());
            MovieResponse parsingMovieInfo = parsingMovieInfo(movieResponse);
            Movie movie = parsingMovieInfo.getMovieInfoResult().getMovieInfo();
            log.info("saveBoxOffice"+boxOfficeMovie.getBoxOfficeId());
            boxOfficeFactory.saveBoxOffice("", boxOfficeMovie);

            if (verifyExistMovie(boxOfficeMovie.getMovieCd())) continue;
            else {
                String movieNm = dailyList.get(i).getMovieNm();
                String movieCd = dailyList.get(i).getMovieCd();
                String outputDate = targetDt;
                MovieDetails movieDetails = tmdbService.getMovieDetailsByTitle(movieNm);
                movie.setBackDrop(movieDetails.getBackdropPath());
                movieRepository.save(movie);
                saveActors(movie);
                saveGenres(movie);
                saveNations(movie);
                saveMovieAudit(movie);
                saveDirectors(movie);

            }
        }
        for (int i = 0; i < dailyList.size(); i++) {
            BoxOfficeMovie boxOfficeMovie = kList.get(i);
            boxOfficeMovie.setBoxOfficeId(i);
            movieResponse = service.getMovieInfo(true, boxOfficeMovie.getMovieCd());
            MovieResponse parsingMovieInfo = parsingMovieInfo(movieResponse);
            Movie movie = parsingMovieInfo.getMovieInfoResult().getMovieInfo();
            boxOfficeFactory.saveBoxOffice("K", boxOfficeMovie);

            if (verifyExistMovie(boxOfficeMovie.getMovieCd())) continue;
            else {
                String movieNm = dailyList.get(i).getMovieNm();
                String movieCd = dailyList.get(i).getMovieCd();
                String outputDate = targetDt;
                MovieDetails movieDetails = tmdbService.getMovieDetailsByTitle(movieNm);
                movie.setBackDrop(movieDetails.getBackdropPath());
                movieRepository.save(movie);
                saveActors(movie);
                saveGenres(movie);
                saveNations(movie);
                saveMovieAudit(movie);
                saveDirectors(movie);

            }
        }
        for (int i = 0; i < dailyList.size(); i++) {
            BoxOfficeMovie boxOfficeMovie = fList.get(i);
            boxOfficeMovie.setBoxOfficeId(i);
            movieResponse = service.getMovieInfo(true, boxOfficeMovie.getMovieCd());
            MovieResponse parsingMovieInfo = parsingMovieInfo(movieResponse);
            Movie movie = parsingMovieInfo.getMovieInfoResult().getMovieInfo();
            boxOfficeFactory.saveBoxOffice("F", boxOfficeMovie);

            if (verifyExistMovie(boxOfficeMovie.getMovieCd())) continue;
            else {
                String movieNm = dailyList.get(i).getMovieNm();
                String movieCd = dailyList.get(i).getMovieCd();
                String outputDate = targetDt;
                MovieDetails movieDetails = tmdbService.getMovieDetailsByTitle(movieNm);
                movie.setBackDrop(movieDetails.getBackdropPath());
                movieRepository.save(movie);
                saveActors(movie);
                saveGenres(movie);
                saveNations(movie);
                saveMovieAudit(movie);
                saveDirectors(movie);

            }
        }
    }


    public boolean verifyExistMovie(String movieCd) {
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
