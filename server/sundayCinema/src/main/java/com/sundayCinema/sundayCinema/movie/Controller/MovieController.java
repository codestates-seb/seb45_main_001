package com.sundayCinema.sundayCinema.movie.Controller;

import com.sundayCinema.sundayCinema.movie.Service.BoxOfficeSwitchService;
import com.sundayCinema.sundayCinema.movie.Service.MovieService;
import com.sundayCinema.sundayCinema.movie.dto.detaiPagelDto.*;
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

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class MovieController {
    private final MovieService movieService;
    private final BoxOfficeMovieRepository boxOfficeMovieRepository;
    private final MovieRepository movieRepository;
    private final MovieDetailsMapper movieDetailsMapper;
    private final BoxOfficeSwitchService boxOfficeSwitchService;

    public MovieController(MovieService movieService, BoxOfficeMovieRepository boxOfficeMovieRepository,
                           MovieRepository movieRepository,
                           MovieDetailsMapper movieDetailsMapper, BoxOfficeSwitchService boxOfficeSwitchService) {
        this.movieService = movieService;
        this.boxOfficeMovieRepository = boxOfficeMovieRepository;
        this.movieRepository = movieRepository;
        this.movieDetailsMapper = movieDetailsMapper;
        this.boxOfficeSwitchService = boxOfficeSwitchService;
    }

    @GetMapping("/saveGenre/{date}")
    public void saveGenreAll(@PathVariable String date) throws Exception {
        movieService.saveGenreAll(date);
    }
    @GetMapping("/save/top10")
    public void saveTop10() throws Exception {
        movieService.saveTop10All();
    }

    @GetMapping("/top10") //탑텐 무비 json 형태로 보내준다.
    public ResponseEntity getTop10MainPage() {
        List<BoxOfficeMovieDto>boxOfficeMovieDtos = boxOfficeSwitchService.loadBoxOfficeByBoxNm("");
        List<GenreMovieDto>genreMovieDtos= movieService.loadGenreMovie("종합");
        MainPageDto mainPageDto = new MainPageDto();
        mainPageDto.setBoxofficeList(boxOfficeMovieDtos);
        mainPageDto.setGenreMovieList(genreMovieDtos);
        return new ResponseEntity<>(mainPageDto, HttpStatus.OK);
    }

    @GetMapping("/top10Korean")
    public ResponseEntity getTop10KoreaMainPage() {
        List<BoxOfficeMovieDto>boxOfficeMovieDtos = boxOfficeSwitchService.loadBoxOfficeByBoxNm("K");
        List<GenreMovieDto>genreMovieDtos= movieService.loadGenreMovie("국내");
        MainPageDto mainPageDto = new MainPageDto();
        mainPageDto.setBoxofficeList(boxOfficeMovieDtos);
        mainPageDto.setGenreMovieList(genreMovieDtos);
        return new ResponseEntity<>(mainPageDto, HttpStatus.OK);
    }

    @GetMapping("/top10Foreign")
    public ResponseEntity getTop10ForeignMainPage() {
        List<BoxOfficeMovieDto>boxOfficeMovieDtos =  boxOfficeSwitchService.loadBoxOfficeByBoxNm("F");
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
        DetailsMediaInfo detailsMediaInfo= movieDetailsMapper.detailsMediaInfoResponseDto(findMovie);
        DetailPageDto<DetailsMediaInfo> detailPageDto = new DetailPageDto<>();
        detailPageDto.setDetailsList(detailsMediaInfo);
        return new ResponseEntity(detailPageDto,HttpStatus.OK);
    }

}
