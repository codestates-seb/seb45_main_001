package com.sundayCinema.sundayCinema.movie.Controller;

import com.sundayCinema.sundayCinema.movie.Service.MovieService;
import com.sundayCinema.sundayCinema.movie.api.KMDB.KdmbService;
import com.sundayCinema.sundayCinema.movie.api.KOBIS.KobisService;
import com.sundayCinema.sundayCinema.movie.dto.BoxOfficeMovieDto;
import com.sundayCinema.sundayCinema.movie.dto.MainPageDto;
import com.sundayCinema.sundayCinema.movie.entity.BoxOfficeMovie;
import com.sundayCinema.sundayCinema.movie.entity.Movie;
import com.sundayCinema.sundayCinema.movie.entity.Poster;
import com.sundayCinema.sundayCinema.movie.entity.Trailer;
import com.sundayCinema.sundayCinema.movie.mapper.BoxOfficeMovieMapper;
import com.sundayCinema.sundayCinema.movie.repository.BoxOfficeMovieRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping
public class MovieController {

    private final KobisService kobisService;
    private final KdmbService kdmbService;
    private final MovieService movieService;
    private final BoxOfficeMovieMapper mapper;
    private final BoxOfficeMovieRepository boxOfficeMovieRepository;

    public MovieController(KobisService kobisService, KdmbService kdmbService, MovieService movieService,
                           BoxOfficeMovieMapper mapper, BoxOfficeMovieRepository boxOfficeMovieRepository) {
        this.kobisService = kobisService;
        this.kdmbService = kdmbService;
        this.movieService = movieService;
        this.mapper = mapper;
        this.boxOfficeMovieRepository = boxOfficeMovieRepository;
    }

    @GetMapping("/test")
    public void test() throws Exception {
        kobisService.dailyBoxOffice();
        kobisService.dailyKoreaBoxOffice();
        kobisService.dailyForeignBoxOffice();
    }
    @GetMapping("/test2")
    public void test2() throws Exception {

        List<BoxOfficeMovie> boxList= boxOfficeMovieRepository.findAll();
        for(int i=0; i< boxList.size(); i++) {
            String movieNm= boxList.get(i).getMovieNm();
            String openDt= boxList.get(i).getOpenDt();
            DateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            // 출력 형식 지정
            DateFormat outputDateFormat = new SimpleDateFormat("yyyyMMdd");
            // 입력 문자열을 Date 객체로 파싱
            Date date = inputDateFormat.parse(openDt);

            // Date 객체를 원하는 출력 형식으로 포맷팅
            String outputDate = outputDateFormat.format(date);
            kdmbService.generateKdmb(movieNm, outputDate);
        }
    }

//    @GetMapping("/trailer")
//    public String trailer(){
//        String mainUrl = movieService.loadTrailer(1,"메인예고편").getTrailer_url();
//        List<Trailer> trailerList= movieService.loadTrailerList(1);
//        for (int i=0; i<trailerList.size(); i++){
//            mainUrl+=", " + trailerList.get(i).getTrailer_url();
//        }
//        return mainUrl;
//    }
    ////////////////////////////////////////////////////////////////////////////////

    @GetMapping("/top10") //탑텐 무비 json 형태로 보내준다.
    public ResponseEntity getTop10Movies() {
        List<BoxOfficeMovieDto> boxOfficeMovieDtos= new ArrayList<>();
       List<BoxOfficeMovie> boxs= movieService.loadBoxOffice(); // 저장 방식을 업데이트가 아닌 누정 방식을 택할 경우 날짜 기준으로 불러올 예정
       for(int i=0; i<boxs.size(); i++){
           BoxOfficeMovie box = boxs.get(i);
           String title = boxs.get(i).getMovieNm();
           Movie findMovie= movieService.loadMovie(title);
           List<Poster> posterList = movieService.loadPosterList(findMovie);
           Poster poster= posterList.get(0);
           BoxOfficeMovieDto boxOfficeMovieDto= mapper.boxOfficeResponseDto(box,poster);
           boxOfficeMovieDtos.add(boxOfficeMovieDto);
       }
        MainPageDto<List<BoxOfficeMovieDto>> mainPageDto = new MainPageDto<>();
        mainPageDto.setBoxofficeList(boxOfficeMovieDtos);
        return new ResponseEntity<>(mainPageDto, HttpStatus.OK);
    }

//    @GetMapping("/top10Korean")
//    public ResponseEntity<List<Movie>> getTop10KoreanMovies() {
//        List<Movie> top10Movies = kobisService.dailyBoxOffice();
//        if (!top10KoreanMovies.isEmpty()) {
//            return ResponseEntity.ok(top10KoreanMovies);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @GetMapping("/top10Foreign")
//    public ResponseEntity<List<Movie>> getTop10ForeignMovies() {
//        List<Movie> top10Movies = kobisService.dailyBoxOffice();
//        if (!top10ForeignMovies.isEmpty()) {
//            return ResponseEntity.ok(top10ForeignMovies);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }


}
