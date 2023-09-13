package com.sundayCinema.sundayCinema.movie.Service;


import com.google.api.services.youtube.model.SearchResult;
import com.sundayCinema.sundayCinema.movie.api.apiRepositoryService.KobisRepositoryService;
import com.sundayCinema.sundayCinema.movie.api.apiRepositoryService.YoutubeRepositoryService;


import com.sundayCinema.sundayCinema.movie.api.apiService.KdmbApiService;
import com.sundayCinema.sundayCinema.movie.api.apiService.YoutubeApiService;
import com.sundayCinema.sundayCinema.movie.dto.mainPageDto.BoxOfficeMovieDto;
import com.sundayCinema.sundayCinema.movie.dto.mainPageDto.GenreMovieDto;
import com.sundayCinema.sundayCinema.movie.entity.boxOffice.BoxOfficeMovie;
import com.sundayCinema.sundayCinema.movie.entity.boxOffice.ForeignBoxOffice;
import com.sundayCinema.sundayCinema.movie.entity.boxOffice.KoreaBoxOffice;
import com.sundayCinema.sundayCinema.movie.entity.movieInfo.Movie;

import com.sundayCinema.sundayCinema.movie.mapper.BoxOfficeMovieMapper;
import com.sundayCinema.sundayCinema.movie.mapper.GenreMovieMapper;
import com.sundayCinema.sundayCinema.movie.repository.boxOfficeRepo.BoxOfficeMovieRepository;
import com.sundayCinema.sundayCinema.movie.repository.boxOfficeRepo.ForeignBoxOfficeRepository;
import com.sundayCinema.sundayCinema.movie.repository.boxOfficeRepo.KoreaBoxOfficeRepository;
import com.sundayCinema.sundayCinema.movie.repository.movieInfoRepo.MovieRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class MovieService {

    private final MovieRepository movieRepository;
    private final BoxOfficeMovieRepository boxOfficeMovieRepository;
    private final KoreaBoxOfficeRepository koreaBoxOfficeRepository;
    private final ForeignBoxOfficeRepository foreignBoxOfficeRepository;
    private final KdmbApiService kdmbService;

    private final YoutubeApiService youtubeApiService;
    private final BoxOfficeMovieMapper boxOfficeMovieMapper;
    private final GenreMovieMapper genreMovieMapper;
    private final KobisRepositoryService kobisRepositoryService;
    private final YoutubeRepositoryService youtubeRepositoryService;

    public MovieService(MovieRepository movieRepository, BoxOfficeMovieRepository boxOfficeMovieRepository,
                        KoreaBoxOfficeRepository koreaBoxOfficeRepository, ForeignBoxOfficeRepository foreignBoxOfficeRepository, KdmbApiService kdmbService, YoutubeApiService youtubeApiService, BoxOfficeMovieMapper boxOfficeMovieMapper, GenreMovieMapper genreMovieMapper, KobisRepositoryService kobisRepositoryService,
                        YoutubeRepositoryService youtubeRepositoryService) {
        this.movieRepository = movieRepository;
        this.boxOfficeMovieRepository = boxOfficeMovieRepository;
        this.koreaBoxOfficeRepository = koreaBoxOfficeRepository;
        this.foreignBoxOfficeRepository = foreignBoxOfficeRepository;
        this.kdmbService = kdmbService;
        this.youtubeApiService = youtubeApiService;
        this.boxOfficeMovieMapper = boxOfficeMovieMapper;
        this.genreMovieMapper = genreMovieMapper;
        this.kobisRepositoryService = kobisRepositoryService;
        this.youtubeRepositoryService = youtubeRepositoryService;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void dailyUpdateAll() throws Exception {
        kobisRepositoryService.saveKobis();
        dailyUpdateMedia();
        getReview();
    }
    // 액션, 코메디, 드라마, 애니메이션, 스릴러, 판타지, 멜로/로맨스, 공포(호러), 어드밴처, 범죄
    public List<GenreMovieDto>loadGenreMovie(String nation){
        List<GenreMovieDto>genreMovieDtos=new ArrayList<>();
        List<Movie> movies = new ArrayList<>();

        if(nation.equals("종합"))movies= movieRepository.findAll();
        else if (nation.equals("국내"))movies= movieRepository.findByNationsNationNm("한국");
        else if(nation.equals("해외")) movies=movieRepository.findByNationsNationNmIsNot("한국");

        for (Movie findMovie : movies) {
            if(parsingGenre(findMovie,"범죄")){
                GenreMovieDto genreMovieDto= genreMovieMapper.responseGenreMovieDto(findMovie,"범죄");
                genreMovieDtos.add(genreMovieDto);
            }
            if (parsingGenre(findMovie, "액션")) {
                GenreMovieDto genreMovieDto = genreMovieMapper.responseGenreMovieDto(findMovie, "액션");

                genreMovieDtos.add(genreMovieDto);
            }
            if (parsingGenre(findMovie, "코메디")) {
                GenreMovieDto genreMovieDto = genreMovieMapper.responseGenreMovieDto(findMovie, "코메디");

                genreMovieDtos.add(genreMovieDto);
            }
            if(parsingGenre(findMovie,"애니메이션")){
                GenreMovieDto genreMovieDto= genreMovieMapper.responseGenreMovieDto(findMovie,"애니메이션");
                genreMovieDtos.add(genreMovieDto);
            }
            if(parsingGenre(findMovie,"판타지")){
                GenreMovieDto genreMovieDto= genreMovieMapper.responseGenreMovieDto(findMovie,"판타지");
                genreMovieDtos.add(genreMovieDto);
            }
            if(parsingGenre(findMovie,"멜로/로맨스")){
                GenreMovieDto genreMovieDto= genreMovieMapper.responseGenreMovieDto(findMovie,"멜로/로맨스");
                genreMovieDtos.add(genreMovieDto);
            }
            if(parsingGenre(findMovie,"공포(호러)")){
                GenreMovieDto genreMovieDto= genreMovieMapper.responseGenreMovieDto(findMovie,"공포(호러)");
                genreMovieDtos.add(genreMovieDto);
            }
            if(parsingGenre(findMovie,"어드밴처")){
                GenreMovieDto genreMovieDto= genreMovieMapper.responseGenreMovieDto(findMovie,"어드밴처");
                genreMovieDtos.add(genreMovieDto);
            }
        }
        return genreMovieDtos;
    }
    public List<BoxOfficeMovieDto> loadBoxOffice() {

        List<BoxOfficeMovieDto> boxOfficeMovieDtos = new ArrayList<>();
        List<BoxOfficeMovie> boxs = boxOfficeMovieRepository.findAll(); // 저장 방식을 업데이트가 아닌 누적 방식을 택할 경우 날짜 기준으로 불러올 예정
        for (int i = 0; i < boxs.size(); i++) {
            BoxOfficeMovie box = boxs.get(i);
            String title = boxs.get(i).getMovieNm();
            Movie findMovie = movieRepository.findByMovieNm(title);

            BoxOfficeMovieDto boxOfficeMovieDto = boxOfficeMovieMapper.boxOfficeResponseDto(box);
            boxOfficeMovieDtos.add(boxOfficeMovieDto);
        }
        return boxOfficeMovieDtos;
    }
    public List<BoxOfficeMovieDto>  loadKoreaBoxOffice() {

        List<BoxOfficeMovieDto> boxOfficeMovieDtos = new ArrayList<>();
        List<KoreaBoxOffice> boxs = koreaBoxOfficeRepository.findAll(); // 저장 방식을 업데이트가 아닌 누적 방식을 택할 경우 날짜 기준으로 불러올 예정
        for (int i = 0; i < boxs.size(); i++) {
            KoreaBoxOffice box = boxs.get(i);
            String title = boxs.get(i).getMovieNm();
            Movie findMovie = movieRepository.findByMovieNm(title);

            BoxOfficeMovieDto boxOfficeMovieDto = boxOfficeMovieMapper.boxOfficeResponseDto(box);
            boxOfficeMovieDtos.add(boxOfficeMovieDto);
        }
        return boxOfficeMovieDtos;
    }
    public List<BoxOfficeMovieDto> loadForeignBoxOffice() {

        List<BoxOfficeMovieDto> boxOfficeMovieDtos = new ArrayList<>();
        List<ForeignBoxOffice> boxs = foreignBoxOfficeRepository.findAll(); // 저장 방식을 업데이트가 아닌 누적 방식을 택할 경우 날짜 기준으로 불러올 예정
        for (int i = 0; i < boxs.size(); i++) {
            ForeignBoxOffice box = boxs.get(i);
            String title = boxs.get(i).getMovieNm();
            Movie findMovie = movieRepository.findByMovieNm(title);

            BoxOfficeMovieDto boxOfficeMovieDto = boxOfficeMovieMapper.boxOfficeResponseDto(box);
            boxOfficeMovieDtos.add(boxOfficeMovieDto);
        }
        return boxOfficeMovieDtos;
    }
    public void dailyUpdateMedia() throws Exception {

        List<BoxOfficeMovie> boxList = boxOfficeMovieRepository.findAll();
        for (int i = 0; i < boxList.size(); i++) {
            String movieNm = boxList.get(i).getMovieNm();

            String openDt = boxList.get(i).getOpenDt();
            String outputDate = parsingDate(openDt);
            kdmbService.generateKdmb(movieNm, outputDate);
        }
        List<KoreaBoxOffice> kList = koreaBoxOfficeRepository.findAll();
        for (int i = 0; i < kList.size(); i++) {
            String movieNm = kList.get(i).getMovieNm();

            String openDt = kList.get(i).getOpenDt();
            String outputDate = parsingDate(openDt);
            kdmbService.generateKdmb(movieNm, outputDate);
        }
        List<ForeignBoxOffice> fList = foreignBoxOfficeRepository.findAll();
        for (int i = 0; i < fList.size(); i++) {
            String movieNm = fList.get(i).getMovieNm();
            String openDt = fList.get(i).getOpenDt();
            String outputDate = parsingDate(openDt);
            kdmbService.generateKdmb( movieNm, outputDate);
        }
    }


    public void getReview() throws GeneralSecurityException, IOException {
        List<BoxOfficeMovie> boxList= boxOfficeMovieRepository.findAll();
        List<KoreaBoxOffice> kList = koreaBoxOfficeRepository.findAll();
        List<ForeignBoxOffice> fList = foreignBoxOfficeRepository.findAll();
        for(int i=0; i< boxList.size(); i++) {
            String movieName = boxList.get(i).getMovieNm();
            Movie findMovie = movieRepository.findByMovieCd(boxList.get(i).getMovieCd());
            List<SearchResult> searchReview = youtubeApiService.searchYoutube(movieName,"리뷰");
            youtubeRepositoryService.saveYoutube(searchReview,findMovie);
        }
        for(int i=0; i< kList.size(); i++) {
            String movieName = kList.get(i).getMovieNm();
            Movie findMovie = movieRepository.findByMovieCd(kList.get(i).getMovieCd());
            List<SearchResult> searchReview = youtubeApiService.searchYoutube(movieName,"리뷰");
            youtubeRepositoryService.saveYoutube(searchReview,findMovie);
        }
        for(int i=0; i< fList.size(); i++) {
            String movieName = fList.get(i).getMovieNm();
            Movie findMovie = movieRepository.findByMovieCd(fList.get(i).getMovieCd());
            List<SearchResult> searchReview = youtubeApiService.searchYoutube(movieName,"리뷰");
            youtubeRepositoryService.saveYoutube(searchReview,findMovie);
        }
    }

    public String parsingDate(String openDt) throws ParseException {
        if (openDt == null || openDt.trim().isEmpty()) {
            // 빈 문자열 또는 null인 경우에 대한 처리
            return "";
        }
        DateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        // 출력 형식 지정
        DateFormat outputDateFormat = new SimpleDateFormat("yyyyMMdd");
        // 입력 문자열을 Date 객체로 파싱
        Date date = inputDateFormat.parse(openDt);

        // Date 객체를 원하는 출력 형식으로 포맷팅
        String outputDate = outputDateFormat.format(date);

        return outputDate;
    }
    public boolean parsingGenre(Movie movie, String genreNm) {
        for (int i = 0; i < movie.getGenres().size(); i++) {
            if (movie.getGenres().get(i).getGenreNm().equals(genreNm)) {
                return true;
            }
        }
        return false;
    }
}