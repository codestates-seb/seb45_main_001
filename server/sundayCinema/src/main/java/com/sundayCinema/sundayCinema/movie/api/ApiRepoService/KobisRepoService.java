package com.sundayCinema.sundayCinema.movie.api.ApiRepoService;


import com.sundayCinema.sundayCinema.movie.Service.BoxOfficeSwitchService;
import com.sundayCinema.sundayCinema.movie.api.KOBIS.KobisService;
import com.sundayCinema.sundayCinema.movie.entity.boxOffice.BoxOfficeMovie;
import com.sundayCinema.sundayCinema.movie.entity.movieInfo.*;
import com.sundayCinema.sundayCinema.movie.repository.boxOfficeRepo.BoxOfficeMovieRepository;
import com.sundayCinema.sundayCinema.movie.repository.boxOfficeRepo.ForeignBoxOfficeRepository;
import com.sundayCinema.sundayCinema.movie.repository.boxOfficeRepo.KoreaBoxOfficeRepository;
import com.sundayCinema.sundayCinema.movie.repository.movieInfoRepo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
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
    private final KobisService kobisService;
    private final BoxOfficeSwitchService boxOfficeSwitchService;

    public KobisRepoService(MovieRepository movieRepository, ActorRepository actorRepository,
                            DirectorRepository directorRepository, MovieAuditRepository movieAuditRepository,
                            NationRepository nationRepository, GenreRepository genreRepository,
                            BoxOfficeMovieRepository boxOfficeMovieRepository, KoreaBoxOfficeRepository koreaBoxOfficeRepository,
                            ForeignBoxOfficeRepository foreignBoxOfficeRepository, KobisService kobisService
            , BoxOfficeSwitchService boxOfficeSwitchService) {
        this.movieRepository = movieRepository;
        this.actorRepository = actorRepository;
        this.directorRepository = directorRepository;
        this.movieAuditRepository = movieAuditRepository;
        this.nationRepository = nationRepository;
        this.genreRepository = genreRepository;
        this.boxOfficeMovieRepository = boxOfficeMovieRepository;
        this.koreaBoxOfficeRepository = koreaBoxOfficeRepository;
        this.foreignBoxOfficeRepository = foreignBoxOfficeRepository;
        this.kobisService = kobisService;
        this.boxOfficeSwitchService = boxOfficeSwitchService;
    }

    public List<BoxOfficeMovie> searchAndSaveBoxOfficeByNationCd(String repNationCd) throws Exception {
        List<BoxOfficeMovie> returnBoxList = new ArrayList<>();
        List<BoxOfficeMovie> boxOfficeList = kobisService.searchingTop10BoxOffice(repNationCd); //검색
        saveMovieDetails(boxOfficeList);
        switch (repNationCd) {                                                                  //기존 자료 삭제
            case "":
                boxOfficeMovieRepository.deleteAll();
                break;
            case "K":
                koreaBoxOfficeRepository.deleteAll();
                break;
            case "F":
                foreignBoxOfficeRepository.deleteAll();
                break;
            default:
                // 지원하지 않는 타입
                throw new IllegalArgumentException("Unsupported box office type: " + repNationCd);
        }

        for (BoxOfficeMovie boxOfficeMovie : boxOfficeList) {
            Movie movie = movieRepository.findByMovieCd(boxOfficeMovie.getMovieCd());
            boxOfficeMovie.setMovie(movie);
            returnBoxList.add(boxOfficeMovie);
            boxOfficeSwitchService.saveBoxOfficeByNationCd(repNationCd, boxOfficeMovie); // 저장
        }
        log.info("returnBoxList :" + returnBoxList.get(0) + returnBoxList.get(1) + returnBoxList.get(2) + returnBoxList.get(3));
        return returnBoxList;
    }

    public List<BoxOfficeMovie> saveGenreBox(String targetDt) throws Exception {
        List<BoxOfficeMovie> searchingGenreBoxList = kobisService.searchingGenreBoxOffice(targetDt); //장르별 영화용 박스오피스 검색
        List<BoxOfficeMovie> notDuplicationGenreList = new ArrayList<>();
        for (BoxOfficeMovie genreMovie : searchingGenreBoxList) {
            String genreMovieCd = genreMovie.getMovieCd();
            if (!verifyExistMovie(genreMovieCd)) {// 중복 검사를 통과한 리스트
                notDuplicationGenreList.add(genreMovie);
            }
        }
        saveMovieDetails(notDuplicationGenreList);
        for (BoxOfficeMovie notDuplicationMovie : notDuplicationGenreList) {

            Movie movie = movieRepository.findByMovieCd(notDuplicationMovie.getMovieCd());
            notDuplicationMovie.setMovie(movie);
            boxOfficeSwitchService.saveBoxOfficeByNationCd("G", notDuplicationMovie);
        }
        return notDuplicationGenreList;
}


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
