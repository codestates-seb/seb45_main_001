package com.sundayCinema.sundayCinema.movie.Controller;


import com.sundayCinema.sundayCinema.movie.Service.MovieService;
import com.sundayCinema.sundayCinema.movie.api.apiRepositoryService.KobisRepositoryService;
import com.sundayCinema.sundayCinema.movie.dto.detailPageDto.DetailPageDto;
import com.sundayCinema.sundayCinema.movie.dto.detailPageDto.DetailsBasicInfo;
import com.sundayCinema.sundayCinema.movie.dto.detailPageDto.DetailsMainInfo;
import com.sundayCinema.sundayCinema.movie.dto.detailPageDto.DetailsMediaInfo;
import com.sundayCinema.sundayCinema.movie.dto.mainPageDto.BoxOfficeMovieDto;
import com.sundayCinema.sundayCinema.movie.dto.mainPageDto.GenreMovieDto;
import com.sundayCinema.sundayCinema.movie.dto.mainPageDto.MainPageDto;
import com.sundayCinema.sundayCinema.movie.entity.boxOffice.BoxOfficeMovie;
import com.sundayCinema.sundayCinema.movie.entity.movieInfo.Movie;
import com.sundayCinema.sundayCinema.movie.mapper.MovieDetailsMapper;
import com.sundayCinema.sundayCinema.movie.repository.boxOfficeRepo.BoxOfficeMovieRepository;
import com.sundayCinema.sundayCinema.movie.repository.movieInfoRepo.MovieRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping
public class MovieController {
    private final MovieService movieService;
    private final BoxOfficeMovieRepository boxOfficeMovieRepository;
    private final MovieRepository movieRepository;
    private final MovieDetailsMapper movieDetailsMapper;
    private final KobisRepositoryService kobisRepositoryService;

    public MovieController(MovieService movieService, BoxOfficeMovieRepository boxOfficeMovieRepository,
                           MovieRepository movieRepository,
                           MovieDetailsMapper movieDetailsMapper, KobisRepositoryService kobisRepositoryService) {
        this.movieService = movieService;
        this.boxOfficeMovieRepository = boxOfficeMovieRepository;
        this.movieRepository = movieRepository;
        this.movieDetailsMapper = movieDetailsMapper;
        this.kobisRepositoryService = kobisRepositoryService;
    }

    @GetMapping("/test")
    public void test() throws Exception {
        kobisRepositoryService.saveKobis();
    }

    @GetMapping("/test2")
    public void test2() throws Exception {
        movieService.dailyUpdateMedia();
    }
    @GetMapping("/save/{date}")
    public void saveMovieData(@PathVariable String date) throws Exception {
        kobisRepositoryService.saveBoxOfficeAndMovieInfo(date);
    }
    @GetMapping("/test3")
    public void test3() throws Exception {
        movieService.dailyUpdateAll();
    }
    @GetMapping("/test4")
    public void test4() throws Exception {
        movieService.getReview();
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
