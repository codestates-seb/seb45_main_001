<<<<<<< HEAD
//package com.sundayCinema.sundayCinema.movie.Controller;
//
//import com.sundayCinema.sundayCinema.movie.api.KOBIS.OpenApiService;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping
//public class MovieController {
//
//    private final OpenApiService openApiService;
//
//    public MovieController(OpenApiService openApiService) {
//        this.openApiService = openApiService;
//    }
//
//    /*
//             1.GET 홈화면(메인페이지)에서 Top 10 박스오피스 출력
//                  "id" : 1,
//                    "movieNm" : "오펜하이머",
//                     "poster_image_url" : "~~~~~",
//                      "trailer_url" : "~~~~~~",
//                         "rank" : "1",
//                         "region" : "해외"
//
//         */
//    @GetMapping("/")
//    public void test(){
//        openApiService.dailyBoxOffice();
//    }
//}
=======
package com.sundayCinema.sundayCinema.movie.Controller;

import com.sundayCinema.sundayCinema.movie.Service.MovieService;
import com.sundayCinema.sundayCinema.movie.api.KMDB.KdmbService;
import com.sundayCinema.sundayCinema.movie.api.KOBIS.KobisService;
import com.sundayCinema.sundayCinema.movie.dto.DetailPageDto;
import com.sundayCinema.sundayCinema.movie.dto.DetailsBasicInfo;
import com.sundayCinema.sundayCinema.movie.dto.DetailsMainInfo;
import com.sundayCinema.sundayCinema.movie.dto.DetailsMediaInfo;
import com.sundayCinema.sundayCinema.movie.dto.BoxOfficeMovieDto;
import com.sundayCinema.sundayCinema.movie.dto.MainPageDto;
import com.sundayCinema.sundayCinema.movie.entity.BoxOfficeMovie;
import com.sundayCinema.sundayCinema.movie.entity.ForeignBoxOffice;
import com.sundayCinema.sundayCinema.movie.entity.KoreaBoxOffice;
import com.sundayCinema.sundayCinema.movie.entity.Movie;
import com.sundayCinema.sundayCinema.movie.entity.Poster;
import com.sundayCinema.sundayCinema.movie.mapper.BoxOfficeMovieMapper;
import com.sundayCinema.sundayCinema.movie.mapper.MovieDetailsMapper;
import com.sundayCinema.sundayCinema.movie.repository.BoxOfficeMovieRepository;
import com.sundayCinema.sundayCinema.movie.repository.MovieRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    private final MovieDetailsMapper movieDetailsMapper;

    public MovieController(KobisService kobisService, KdmbService kdmbService,
                           MovieService movieService, BoxOfficeMovieMapper boxOfficeMovieMapper,
                           BoxOfficeMovieRepository boxOfficeMovieRepository, MovieRepository movieRepository,
                           MovieDetailsMapper movieDetailsMapper) {
        this.kobisService = kobisService;
        this.kdmbService = kdmbService;
        this.movieService = movieService;
        this.boxOfficeMovieMapper = boxOfficeMovieMapper;
        this.boxOfficeMovieRepository = boxOfficeMovieRepository;
        this.movieRepository = movieRepository;
        this.movieDetailsMapper = movieDetailsMapper;
    }

    @GetMapping("/test")
    public void test() throws Exception {
        kobisService.dailyUpdateBoxOffice("");
        kobisService.dailyUpdateBoxOffice("K");
        kobisService.dailyUpdateBoxOffice("F");
    }

    @GetMapping("/test2")
    public void test2() throws Exception {
        movieService.dailyUpdateMedia();
    }

    @GetMapping("/test3")
    public void test3() throws Exception {
        movieService.dailyUpdateAll();
    }


    @GetMapping("/") //탑텐 무비 json 형태로 보내준다.
    public ResponseEntity getTop10Movies() {
        List<BoxOfficeMovieDto> boxOfficeMovieDtos = new ArrayList<>();
        List<BoxOfficeMovie> boxs = movieService.loadBoxOffice(); // 저장 방식을 업데이트가 아닌 누적 방식을 택할 경우 날짜 기준으로 불러올 예정
        for (int i = 0; i < boxs.size(); i++) {
            BoxOfficeMovie box = boxs.get(i);
            String title = boxs.get(i).getMovieNm();
            Movie findMovie = movieService.loadMovie(title);
            List<Poster> posterList = movieService.loadPosterList(findMovie);
            Poster poster = posterList.get(0);
            BoxOfficeMovieDto boxOfficeMovieDto = boxOfficeMovieMapper.boxOfficeResponseDto(box);
            boxOfficeMovieDtos.add(boxOfficeMovieDto);
        }
        MainPageDto<List<BoxOfficeMovieDto>> mainPageDto = new MainPageDto<>();
        mainPageDto.setBoxofficeList(boxOfficeMovieDtos);
        return new ResponseEntity<>(mainPageDto, HttpStatus.OK);
    }

    @GetMapping("/top10Korean")
    public ResponseEntity getTop10KoreaMovies() {
        List<BoxOfficeMovieDto> boxOfficeMovieDtos = new ArrayList<>();
        List<KoreaBoxOffice> boxs = movieService.loadKoreaBoxOffice(); // 저장 방식을 업데이트가 아닌 누적 방식을 택할 경우 날짜 기준으로 불러올 예정
        for (int i = 0; i < boxs.size(); i++) {
            KoreaBoxOffice box = boxs.get(i);
            String title = boxs.get(i).getMovieNm();
            Movie findMovie = movieService.loadMovie(title);
            List<Poster> posterList = movieService.loadPosterList(findMovie);
            Poster poster = posterList.get(0);
            BoxOfficeMovieDto boxOfficeMovieDto = boxOfficeMovieMapper.boxOfficeResponseDto(box);
            boxOfficeMovieDtos.add(boxOfficeMovieDto);
        }
        MainPageDto<List<BoxOfficeMovieDto>> mainPageDto = new MainPageDto<>();
        mainPageDto.setBoxofficeList(boxOfficeMovieDtos);
        return new ResponseEntity<>(mainPageDto, HttpStatus.OK);
    }

    @GetMapping("/top10Foreign")
    public ResponseEntity getTop10ForeignMovies() {
        List<BoxOfficeMovieDto> boxOfficeMovieDtos = new ArrayList<>();
        List<ForeignBoxOffice> boxs = movieService.loadForeignBoxOffice(); // 저장 방식을 업데이트가 아닌 누적 방식을 택할 경우 날짜 기준으로 불러올 예정
        for (int i = 0; i < boxs.size(); i++) {
            ForeignBoxOffice box = boxs.get(i);
            String title = boxs.get(i).getMovieNm();
            Movie findMovie = movieService.loadMovie(title);
            List<Poster> posterList = movieService.loadPosterList(findMovie);
            Poster poster = posterList.get(0);
            BoxOfficeMovieDto boxOfficeMovieDto = boxOfficeMovieMapper.boxOfficeResponseDto(box);
            boxOfficeMovieDtos.add(boxOfficeMovieDto);
        }
        MainPageDto<List<BoxOfficeMovieDto>> mainPageDto = new MainPageDto<>();
        mainPageDto.setBoxofficeList(boxOfficeMovieDtos);
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
>>>>>>> a8a0389c2ebfab9d514e616169e240966ee9b6a8
