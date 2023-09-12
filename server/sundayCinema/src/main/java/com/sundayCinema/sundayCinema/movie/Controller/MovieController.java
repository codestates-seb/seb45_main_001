package com.sundayCinema.sundayCinema.movie.Controller;

import com.sundayCinema.sundayCinema.movie.Service.MovieService;
import com.sundayCinema.sundayCinema.movie.api.ImageDownloadService;
import com.sundayCinema.sundayCinema.movie.api.KMDB.KdmbService;
import com.sundayCinema.sundayCinema.movie.api.KOBIS.KobisService;
import com.sundayCinema.sundayCinema.movie.api.TMDB.MovieDetails;
import com.sundayCinema.sundayCinema.movie.api.TMDB.TmdbService;
import com.sundayCinema.sundayCinema.movie.dto.*;
import com.sundayCinema.sundayCinema.movie.entity.boxOffice.BoxOfficeMovie;
import com.sundayCinema.sundayCinema.movie.entity.boxOffice.ForeignBoxOffice;
import com.sundayCinema.sundayCinema.movie.entity.boxOffice.KoreaBoxOffice;
import com.sundayCinema.sundayCinema.movie.entity.movieInfo.Movie;
import com.sundayCinema.sundayCinema.movie.entity.movieMedia.Poster;
import com.sundayCinema.sundayCinema.movie.mapper.BoxOfficeMovieMapper;
import com.sundayCinema.sundayCinema.movie.mapper.MovieDetailsMapper;
import com.sundayCinema.sundayCinema.movie.repository.boxOfficeRepo.BoxOfficeMovieRepository;
import com.sundayCinema.sundayCinema.movie.repository.movieInfoRepo.MovieRepository;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.nio.file.Files;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping
public class MovieController {
    private final KobisService kobisService;
    private final KdmbService kdmbService;
    private final MovieService movieService;
    private final BoxOfficeMovieMapper boxOfficeMovieMapper;
    private final BoxOfficeMovieRepository boxOfficeMovieRepository;
    private final MovieRepository movieRepository;
    private final TmdbService tmdbService;
    private final MovieDetailsMapper movieDetailsMapper;
    private final ImageDownloadService imageDownloadService;

    public MovieController(KobisService kobisService, KdmbService kdmbService,
                           MovieService movieService, BoxOfficeMovieMapper boxOfficeMovieMapper,
                           BoxOfficeMovieRepository boxOfficeMovieRepository, MovieRepository movieRepository,
                           TmdbService tmdbService, MovieDetailsMapper movieDetailsMapper,
                           ImageDownloadService imageDownloadService) {
        this.kobisService = kobisService;
        this.kdmbService = kdmbService;
        this.movieService = movieService;
        this.boxOfficeMovieMapper = boxOfficeMovieMapper;
        this.boxOfficeMovieRepository = boxOfficeMovieRepository;
        this.movieRepository = movieRepository;
        this.tmdbService = tmdbService;
        this.movieDetailsMapper = movieDetailsMapper;
        this.imageDownloadService = imageDownloadService;
    }

    @GetMapping("/test")
    public void test() throws Exception {
        kobisService.saveKobis();
    }

    @GetMapping("/test2")
    public void test2() throws Exception {
        movieService.dailyUpdateMedia();
    }
    @GetMapping("/save/{date}")
    public void saveMovieData(@PathVariable String date) throws Exception {
        kobisService.saveBoxOffice(date);
    }
    @GetMapping("/test3")
    public void test3() throws Exception {
        movieService.dailyUpdateAll();
    }
    @GetMapping("/download")
    public void downloadImage() throws IOException {
        imageDownloadService.downloadImage("http://file.koreafilm.or.kr/thm/01/copy/00/65/19/tn_DST817433.jpg","C:\\sundayImages\\image.jpg");
    }
    @GetMapping("/tmdb/{movieTitle}")
    public String getMovieDetails(@PathVariable String movieTitle) throws IOException {
        tmdbService.getMovieDetailsByTitle(movieTitle);
        tmdbService.getMovieTrailer(movieTitle);
//        tmdbService.getCollectionIdByMovieTitle(movieTitle);
        log.info("제목 :"+ tmdbService.getMovieDetailsByTitle(movieTitle).getTitle());
        log.info("배경화면 :"+tmdbService.getMovieDetailsByTitle(movieTitle).getBackdropPath());
        log.info("줄거리 :"+tmdbService.getMovieDetailsByTitle(movieTitle).getOverview());
        log.info("포스터 :"+tmdbService.getMovieDetailsByTitle(movieTitle).getPosterPath());
        log.info("영화 예고편 :"+ tmdbService.getMovieTrailer(movieTitle));
//        log.info("이미지 컬렉션 :" + tmdbService.getCollectionIdByMovieTitle(movieTitle));
        return tmdbService.getMovieDetailsByTitle(movieTitle).getTitle();
    }
    @GetMapping("/top10") //탑텐 무비 json 형태로 보내준다.
    public ResponseEntity getTop10Movies() {
        List<BoxOfficeMovieDto>boxOfficeMovieDtos =  movieService.loadBoxOffice();
        List<GenreMovieDto>genreMovieDtos= movieService.loadGenreMovie("종합");
        MainPageDto mainPageDto = new MainPageDto();
        mainPageDto.setBoxofficeList(boxOfficeMovieDtos);
        mainPageDto.setGenreMovieList(genreMovieDtos);
        return new ResponseEntity<>(mainPageDto, HttpStatus.OK);
    }

    @GetMapping("/top10Korean")
    public ResponseEntity getTop10KoreaMovies() {
        List<BoxOfficeMovieDto>boxOfficeMovieDtos =  movieService.loadKoreaBoxOffice();
        List<GenreMovieDto>genreMovieDtos= movieService.loadGenreMovie("국내");
        MainPageDto mainPageDto = new MainPageDto();
        mainPageDto.setBoxofficeList(boxOfficeMovieDtos);
        mainPageDto.setGenreMovieList(genreMovieDtos);
        return new ResponseEntity<>(mainPageDto, HttpStatus.OK);
    }

    @GetMapping("/top10Foreign")
    public ResponseEntity getTop10ForeignMovies() {
        List<BoxOfficeMovieDto>boxOfficeMovieDtos =  movieService.loadForeignBoxOffice();
        List<GenreMovieDto>genreMovieDtos= movieService.loadGenreMovie("해외");
        MainPageDto mainPageDto = new MainPageDto();
        mainPageDto.setBoxofficeList(boxOfficeMovieDtos);
        mainPageDto.setGenreMovieList(genreMovieDtos);
        return new ResponseEntity<>(mainPageDto, HttpStatus.OK);
    }
    @GetMapping("/details/{movieId}")
    public ResponseEntity getMovieDetails(@PathVariable long movieId){

        Movie findMovie = movieRepository.findByMovieId(movieId);
        String movieCd = findMovie.getMovieCd();
        BoxOfficeMovie boxOfficeMovie = boxOfficeMovieRepository.findByMovieCd(movieCd);
        DetailsBasicInfo basicInfo = movieDetailsMapper.detailsBasicResponseDto(boxOfficeMovie, findMovie);
        DetailPageDto<DetailsBasicInfo> detailPageDto = new DetailPageDto<>();
        detailPageDto.setDetailsList(basicInfo);
        return new ResponseEntity(detailPageDto,HttpStatus.OK);
    }
    @GetMapping("/details/{movieId}/mainInfo")
    public ResponseEntity getMovieDetailsMainInfo(@PathVariable long movieId){

        Movie findMovie = movieRepository.findByMovieId(movieId);
        DetailsMainInfo detailsMainInfo= movieDetailsMapper.detailsMainInfoResponseDto(findMovie);
        DetailPageDto<DetailsMainInfo> detailPageDto = new DetailPageDto<>();
        detailPageDto.setDetailsList(detailsMainInfo);
        return new ResponseEntity(detailPageDto,HttpStatus.OK);
    }

    @GetMapping("/details/{movieId}/mediaInfo")
    public ResponseEntity getMovieDetailsMediaInfo(@PathVariable long movieId){

        Movie findMovie = movieRepository.findByMovieId(movieId);
        log.info("찾은 영화 :"+ findMovie.getMovieNm());
        DetailsMediaInfo detailsMediaInfo= movieDetailsMapper.detailsMediaInfoResponseDto(findMovie);
        DetailPageDto<DetailsMediaInfo> detailPageDto = new DetailPageDto<>();
        detailPageDto.setDetailsList(detailsMediaInfo);
        return new ResponseEntity(detailPageDto,HttpStatus.OK);
    }

}
