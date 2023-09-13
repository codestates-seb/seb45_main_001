package com.sundayCinema.sundayCinema.movie.api.apiRepositoryService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.services.youtube.model.SearchResult;
import com.sundayCinema.sundayCinema.movie.Service.BoxOfficeService;
import com.sundayCinema.sundayCinema.movie.api.apiResponse.MovieResponse;
import com.sundayCinema.sundayCinema.movie.api.apiResponse.TmdbMovieDetails;

import com.sundayCinema.sundayCinema.movie.api.apiService.KdmbApiService;
import com.sundayCinema.sundayCinema.movie.api.apiService.KobisApiService;
import com.sundayCinema.sundayCinema.movie.api.apiService.TmdbApiService;
import com.sundayCinema.sundayCinema.movie.api.apiService.YoutubeApiService;
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

import java.util.List;

@Service
@Slf4j
public class KobisRepositoryService {

    @Value("${KOBIS_API_KEY}")
    private String KobisApiKEY;
    String movieResponse = "";
    private final KobisApiService kobisApiService;
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
    private final KdmbApiService kdmbService;
    private final TmdbApiService tmdbApiService;

    private final YoutubeRepositoryService youtubeRepositoryService;
    private final YoutubeApiService youtubeApiService;

    public KobisRepositoryService(KobisApiService kobisApiService, MovieRepository movieRepository,
                                  ActorRepository actorRepository, DirectorRepository directorRepository,
                                  MovieAuditRepository movieAuditRepository, NationRepository nationRepository,
                                  GenreRepository genreRepository, BoxOfficeService boxOfficeFactory,
                                  BoxOfficeMovieRepository boxOfficeMovieRepository, KoreaBoxOfficeRepository koreaBoxOfficeRepository,
                                  ForeignBoxOfficeRepository foreignBoxOfficeRepository, KdmbApiService kdmbService, TmdbApiService tmdbApiService, YoutubeRepositoryService youtubeRepositoryService, YoutubeApiService youtubeApiService) {
        this.kobisApiService = kobisApiService;
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
        this.tmdbApiService = tmdbApiService;
        this.youtubeRepositoryService = youtubeRepositoryService;
        this.youtubeApiService = youtubeApiService;
    }

    public void saveBoxOfficeAndMovieInfo(String targetDt) throws Exception {
        KobisOpenAPIRestService service = new KobisOpenAPIRestService(KobisApiKEY);
        List<BoxOfficeMovie> dailyList= kobisApiService.searchingBoxOffice(targetDt);

        for (int i = 0; i < dailyList.size(); i++) {
            BoxOfficeMovie boxOfficeMovie = dailyList.get(i);
            boxOfficeMovie.setBoxOfficeId(i);

            if (movieRepository.existsByMovieCd(boxOfficeMovie.getMovieCd())) continue;
            else {
                movieResponse = service.getMovieInfo(true, boxOfficeMovie.getMovieCd());
                MovieResponse parsingMovieInfo = parsingMovieInfo(movieResponse);
                Movie movie = parsingMovieInfo.getMovieInfoResult().getMovieInfo();
                TmdbMovieDetails tmdbMovieDetails= tmdbApiService.getMovieDetailsByTitle(movie.getMovieNm());
                movie.setPoster(tmdbMovieDetails.getPosterPath());
                movie.setBackDrop(tmdbMovieDetails.getBackdropPath());
                movie.setPlot(tmdbMovieDetails.getOverview());
                movieRepository.save(movie);
                saveActors(movie);
                saveGenres(movie);
                saveNations(movie);
                saveMovieAudit(movie);
                saveDirectors(movie);
                String movieNm = dailyList.get(i).getMovieNm();
                log.info("movieNm: " + movieNm);
                String outputDate = targetDt;
                kdmbService.generateKdmb(movieNm, outputDate);
                List<SearchResult> searchResults= youtubeApiService.searchYoutube(movieNm,"리뷰");
                youtubeRepositoryService.saveYoutube(searchResults,movie);
            }
        }
    }

    public void saveKobis() throws Exception {
        KobisOpenAPIRestService service = new KobisOpenAPIRestService(KobisApiKEY);
        boxOfficeMovieRepository.deleteAll();
        koreaBoxOfficeRepository.deleteAll();
        foreignBoxOfficeRepository.deleteAll();
        List<BoxOfficeMovie> dailyList = kobisApiService.searchingDailyBoxOffice("");
        List<BoxOfficeMovie> kList = kobisApiService.searchingDailyBoxOffice("K");
        List<BoxOfficeMovie> fList = kobisApiService.searchingDailyBoxOffice("F");
        for (int i = 0; i < dailyList.size(); i++) {
            BoxOfficeMovie boxOfficeMovie = dailyList.get(i);
            boxOfficeFactory.saveBoxOffice("", boxOfficeMovie);

            if (movieRepository.existsByMovieCd(boxOfficeMovie.getMovieCd())) continue;
            else {
                movieResponse = service.getMovieInfo(true, boxOfficeMovie.getMovieCd());
                MovieResponse parsingMovieInfo = parsingMovieInfo(movieResponse);
                Movie movie = parsingMovieInfo.getMovieInfoResult().getMovieInfo();
                TmdbMovieDetails tmdbMovieDetails= tmdbApiService.getMovieDetailsByTitle(movie.getMovieNm());
                movie.setPoster(tmdbMovieDetails.getPosterPath());
                movie.setBackDrop(tmdbMovieDetails.getBackdropPath());
                movie.setPlot(tmdbMovieDetails.getOverview());
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
            boxOfficeFactory.saveBoxOffice("K", boxOfficeMovie);

            if (movieRepository.existsByMovieCd(boxOfficeMovie.getMovieCd())) continue;
            else {
                movieResponse = service.getMovieInfo(true, boxOfficeMovie.getMovieCd());
                MovieResponse parsingMovieInfo = parsingMovieInfo(movieResponse);
                Movie movie = parsingMovieInfo.getMovieInfoResult().getMovieInfo();
                TmdbMovieDetails tmdbMovieDetails= tmdbApiService.getMovieDetailsByTitle(movie.getMovieNm());
                movie.setPoster(tmdbMovieDetails.getPosterPath());
                movie.setBackDrop(tmdbMovieDetails.getBackdropPath());
                movie.setPlot(tmdbMovieDetails.getOverview());
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
            boxOfficeFactory.saveBoxOffice("F", boxOfficeMovie);

            if (movieRepository.existsByMovieCd(boxOfficeMovie.getMovieCd())) continue;
            else {
                movieResponse = service.getMovieInfo(true, boxOfficeMovie.getMovieCd());
                MovieResponse parsingMovieInfo = parsingMovieInfo(movieResponse);
                Movie movie = parsingMovieInfo.getMovieInfoResult().getMovieInfo();
                TmdbMovieDetails tmdbMovieDetails= tmdbApiService.getMovieDetailsByTitle(movie.getMovieNm());
                movie.setPoster(tmdbMovieDetails.getPosterPath());
                movie.setBackDrop(tmdbMovieDetails.getBackdropPath());
                movie.setPlot(tmdbMovieDetails.getOverview());
                movieRepository.save(movie);
                saveActors(movie);
                saveGenres(movie);
                saveNations(movie);
                saveMovieAudit(movie);
                saveDirectors(movie);
            }
        }
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


    public MovieResponse parsingMovieInfo(String movieResponse) throws JsonProcessingException {
        String jsonString = movieResponse;

        ObjectMapper objectMapper = new ObjectMapper();
        MovieResponse movieData = objectMapper.readValue(jsonString, MovieResponse.class);

        return movieData;
    }
}
