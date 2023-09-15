package com.sundayCinema.sundayCinema.movie.api.ApiRepoService;


import com.sundayCinema.sundayCinema.movie.Service.BoxOfficeService;
import com.sundayCinema.sundayCinema.movie.api.KMDB.KdmbService;
import com.sundayCinema.sundayCinema.movie.api.KOBIS.KobisService;
import com.sundayCinema.sundayCinema.movie.entity.boxOffice.BoxOfficeMovie;
import com.sundayCinema.sundayCinema.movie.entity.movieInfo.*;
import com.sundayCinema.sundayCinema.movie.repository.boxOfficeRepo.BoxOfficeMovieRepository;
import com.sundayCinema.sundayCinema.movie.repository.boxOfficeRepo.ForeignBoxOfficeRepository;
import com.sundayCinema.sundayCinema.movie.repository.boxOfficeRepo.GenreBoxOfficeRepository;
import com.sundayCinema.sundayCinema.movie.repository.boxOfficeRepo.KoreaBoxOfficeRepository;
import com.sundayCinema.sundayCinema.movie.repository.movieInfoRepo.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KobisRepoService {

    private final MovieRepository movieRepository;
    private final ActorRepository actorRepository;
    private final DirectorRepository directorRepository;
    private final MovieAuditRepository movieAuditRepository;
    private final NationRepository nationRepository;
    private final GenreRepository genreRepository;

    private final BoxOfficeMovieRepository boxOfficeMovieRepository;
    private final KoreaBoxOfficeRepository koreaBoxOfficeRepository;
    private final ForeignBoxOfficeRepository foreignBoxOfficeRepository;

    private final KdmbService kdmbService;

    private final KobisService kobisService;

    private final GenreBoxOfficeRepository genreBoxOfficeRepository;

    private final BoxOfficeService boxOfficeService;

    public KobisRepoService(MovieRepository movieRepository, ActorRepository actorRepository,
                            DirectorRepository directorRepository, MovieAuditRepository movieAuditRepository,
                            NationRepository nationRepository, GenreRepository genreRepository,
                            BoxOfficeMovieRepository boxOfficeMovieRepository, KoreaBoxOfficeRepository koreaBoxOfficeRepository,
                            ForeignBoxOfficeRepository foreignBoxOfficeRepository, KdmbService kdmbService, KobisService kobisService,
                            GenreBoxOfficeRepository genreBoxOfficeRepository, BoxOfficeService boxOfficeService) {
        this.movieRepository = movieRepository;
        this.actorRepository = actorRepository;
        this.directorRepository = directorRepository;
        this.movieAuditRepository = movieAuditRepository;
        this.nationRepository = nationRepository;
        this.genreRepository = genreRepository;
        this.boxOfficeMovieRepository = boxOfficeMovieRepository;
        this.koreaBoxOfficeRepository = koreaBoxOfficeRepository;
        this.foreignBoxOfficeRepository = foreignBoxOfficeRepository;
        this.kdmbService = kdmbService;
        this.kobisService = kobisService;
        this.genreBoxOfficeRepository = genreBoxOfficeRepository;
        this.boxOfficeService = boxOfficeService;
    }

    public List<BoxOfficeMovie> saveGenreBox(String targetDt) throws Exception {
        List<BoxOfficeMovie> returnBoxList = new ArrayList<>();
        List<BoxOfficeMovie> genreBoxList = kobisService.searchingGenreBoxOffice(targetDt);
        saveMovieDetails(genreBoxList);
        for (int i = 0; i < genreBoxList.size(); i++) {
            BoxOfficeMovie boxOfficeMovie = genreBoxList.get(i);
            Movie movie= movieRepository.findByMovieCd(boxOfficeMovie.getMovieCd());
            boxOfficeMovie.setMovie(movie);
            returnBoxList.add(boxOfficeMovie);
            boxOfficeService.saveBoxOffice("G", boxOfficeMovie); //박스 오피스 타입을 변환해서 저장
        }
        return returnBoxList;
    }

    public List<BoxOfficeMovie>  saveTop10Box() throws Exception {

        boxOfficeMovieRepository.deleteAll();
        List<BoxOfficeMovie> returnBoxList = new ArrayList<>();
        List<BoxOfficeMovie> dailyList = kobisService.searchingTop10BoxOffice("");
        saveMovieDetails(dailyList);
        for (int i = 0; i < dailyList.size(); i++) {
            BoxOfficeMovie boxOfficeMovie = dailyList.get(i);
            Movie movie= movieRepository.findByMovieCd(boxOfficeMovie.getMovieCd());
            boxOfficeMovie.setMovie(movie);
            returnBoxList.add(boxOfficeMovie);
            boxOfficeService.saveBoxOffice("", boxOfficeMovie);
        }
        return returnBoxList;
    }

    public List<BoxOfficeMovie> saveKoreaTop10Box() throws Exception {

        koreaBoxOfficeRepository.deleteAll();
        List<BoxOfficeMovie> returnBoxList = new ArrayList<>();
        List<BoxOfficeMovie> kList = kobisService.searchingTop10BoxOffice("K");
        saveMovieDetails(kList);
        for (int i = 0; i < kList.size(); i++) {
            BoxOfficeMovie boxOfficeMovie = kList.get(i);
            Movie movie= movieRepository.findByMovieCd(boxOfficeMovie.getMovieCd());
            boxOfficeMovie.setMovie(movie);
            returnBoxList.add(boxOfficeMovie);
            boxOfficeService.saveBoxOffice("K", boxOfficeMovie);
        }
        return returnBoxList;
    }

    public List<BoxOfficeMovie> saveForeignTop10Box() throws Exception {

        foreignBoxOfficeRepository.deleteAll();
        List<BoxOfficeMovie> returnBoxList = new ArrayList<>();
        List<BoxOfficeMovie> fList = kobisService.searchingTop10BoxOffice("F");
        saveMovieDetails(fList);
        for (int i = 0; i < fList.size(); i++) {
            BoxOfficeMovie boxOfficeMovie = fList.get(i);
            Movie movie= movieRepository.findByMovieCd(boxOfficeMovie.getMovieCd());
            boxOfficeMovie.setMovie(movie);
            returnBoxList.add(boxOfficeMovie);
            boxOfficeService.saveBoxOffice("F", boxOfficeMovie);
        }
        return returnBoxList;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //영화 세부 정보 저장
    public void saveMovieDetails(List<BoxOfficeMovie> boxList) throws Exception {
        List<Movie> movieList = kobisService.searchingMovieInfo(boxList);
        for (int i = 0; i < movieList.size(); i++) {
            if (verifyExistMovie(movieList.get(i).getMovieCd())) {
            }// 박스오피스 객체가 영화 레포에 중복으로 존재하면 아무것도 안함
            else {
                movieRepository.save(movieList.get(i));
                saveDirectors(movieList.get(i));
                saveActors(movieList.get(i));
                saveGenres(movieList.get(i));
                saveMovieAudit(movieList.get(i));
                saveNations(movieList.get(i));
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
                    genre.setGenreId(genreId + i + 1);
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
                    nation.setNationId(nationId + i + 1); // 각 배우에 고유한 ID를 부여 (선택적)
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
                    audit.setAuditId(auditId + i + 1); // 각 배우에 고유한 ID를 부여 (선택적)
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

}
