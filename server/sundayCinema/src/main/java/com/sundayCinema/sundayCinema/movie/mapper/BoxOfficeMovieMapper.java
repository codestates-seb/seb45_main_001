package com.sundayCinema.sundayCinema.movie.mapper;

import com.sundayCinema.sundayCinema.movie.dto.mainPageDto.BoxOfficeMovieDto;
import com.sundayCinema.sundayCinema.movie.dto.mainPageDto.GenreDto;
import com.sundayCinema.sundayCinema.movie.dto.mainPageDto.StillCutDto;
import com.sundayCinema.sundayCinema.movie.entity.boxOffice.BoxOfficeMovie;
import com.sundayCinema.sundayCinema.movie.entity.boxOffice.ForeignBoxOffice;
import com.sundayCinema.sundayCinema.movie.entity.boxOffice.KoreaBoxOffice;
import com.sundayCinema.sundayCinema.movie.entity.movieInfo.Genre;
import com.sundayCinema.sundayCinema.movie.entity.movieInfo.Movie;
import com.sundayCinema.sundayCinema.movie.entity.movieMedia.StillCut;
import com.sundayCinema.sundayCinema.movie.repository.movieInfoRepo.MovieRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class BoxOfficeMovieMapper {
    private final MovieRepository movieRepository;

    public BoxOfficeMovieMapper(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    //todo : boxoffice id로 받을지 말지 고민하기


    public BoxOfficeMovieDto boxOfficeResponseDto(BoxOfficeMovie boxOfficeMovie) {
        ArrayList<GenreDto> genreDtos = new ArrayList<>();
        if (boxOfficeMovie == null) {
            return null;
        }

        String movieCd= boxOfficeMovie.getMovieCd();
        Movie findMovie = movieRepository.findByMovieCd(movieCd);
        for (int i = 0; i < findMovie.getGenres().size(); i++) {
            GenreDto genreDto = new GenreDto();
            Genre genre = findMovie.getGenres().get(i);
            genreDto.setGenreNm(genre.getGenreNm());
            genreDtos.add(genreDto);
        }
        String trailerUrl="";

        if (findMovie.getTrailers().isEmpty()) {
              trailerUrl= "https://www.kmdb.or.kr/trailer/trailerPlayPop?pFileNm=MK006689_P03.mp4";
        }else {
            trailerUrl = findMovie.getTrailers().get(0).getTrailer_url();
        }

        ArrayList<StillCutDto> stillCutDtos=new ArrayList<>();
        for (int i = 0; i < findMovie.getStillCuts().size(); i++) {
            StillCutDto stillCutDto = new StillCutDto();
            StillCut stillCut = findMovie.getStillCuts().get(i);
            stillCutDto.setStillCut_url(stillCut.getStillCut_url());
            stillCutDtos.add(stillCutDto);
        }


        BoxOfficeMovieDto boxOfficeMovieDto = new BoxOfficeMovieDto();
        boxOfficeMovieDto.movieId= findMovie.getMovieId();
        boxOfficeMovieDto.trailerUrl= trailerUrl;
        boxOfficeMovieDto.posterUrl = findMovie.getPoster();
        boxOfficeMovieDto.rank = boxOfficeMovie.getRank();
        boxOfficeMovieDto.movieNm = boxOfficeMovie.getMovieNm();
        boxOfficeMovieDto.genre=genreDtos;
        boxOfficeMovieDto.stillCut=stillCutDtos;
        boxOfficeMovieDto.backDrop = findMovie.getBackDrop();
        if(findMovie.getPlot().isEmpty()){
            boxOfficeMovieDto.plot="줄거리가 없습니다.";
        }
        boxOfficeMovieDto.plot = findMovie.getPlot();
        return boxOfficeMovieDto;
    }
    public BoxOfficeMovieDto boxOfficeResponseDto(KoreaBoxOffice boxOfficeMovie) {
        ArrayList<GenreDto> genreDtos = new ArrayList<>();
        if (boxOfficeMovie == null) {
            return null;
        }

        String movieCd= boxOfficeMovie.getMovieCd();
        Movie findMovie = movieRepository.findByMovieCd(movieCd);
        for (int i = 0; i < findMovie.getGenres().size(); i++) {
            GenreDto genreDto = new GenreDto();
            Genre genre = findMovie.getGenres().get(i);
            genreDto.setGenreNm(genre.getGenreNm());
            genreDtos.add(genreDto);
        }
        String trailerUrl="";
        if (findMovie.getTrailers().isEmpty()) {

            trailerUrl = "https://www.kmdb.or.kr/trailer/trailerPlayPop?pFileNm=MK006689_P03.mp4";
        }else {
            trailerUrl = findMovie.getTrailers().get(0).getTrailer_url();

        }
        ArrayList<StillCutDto> stillCutDtos=new ArrayList<>();
        for (int i = 0; i < findMovie.getStillCuts().size(); i++) {
            StillCutDto stillCutDto = new StillCutDto();
            StillCut stillCut = findMovie.getStillCuts().get(i);
            stillCutDto.setStillCut_url(stillCut.getStillCut_url());
            stillCutDtos.add(stillCutDto);
        }

        BoxOfficeMovieDto boxOfficeMovieDto = new BoxOfficeMovieDto();
        boxOfficeMovieDto.movieId= findMovie.getMovieId();
        boxOfficeMovieDto.trailerUrl= trailerUrl;
        boxOfficeMovieDto.posterUrl = findMovie.getPoster();
        boxOfficeMovieDto.rank = boxOfficeMovie.getRank();
        boxOfficeMovieDto.movieNm = boxOfficeMovie.getMovieNm();
        boxOfficeMovieDto.genre=genreDtos;
        boxOfficeMovieDto.stillCut=stillCutDtos;
        boxOfficeMovieDto.backDrop = findMovie.getBackDrop();
        if(findMovie.getPlot().isEmpty()){
            boxOfficeMovieDto.plot="줄거리가 없습니다.";
        }else boxOfficeMovieDto.plot = findMovie.getPlot();

        return boxOfficeMovieDto;
    }
    public BoxOfficeMovieDto boxOfficeResponseDto(ForeignBoxOffice boxOfficeMovie) {
        ArrayList<GenreDto> genreDtos = new ArrayList<>();
        if (boxOfficeMovie == null) {
            return null;
        }

        String movieCd= boxOfficeMovie.getMovieCd();
        Movie findMovie = movieRepository.findByMovieCd(movieCd);
        for (int i = 0; i < findMovie.getGenres().size(); i++) {
            GenreDto genreDto = new GenreDto();
            Genre genre = findMovie.getGenres().get(i);
            genreDto.setGenreNm(genre.getGenreNm());
            genreDtos.add(genreDto);
        }
        String trailerUrl="";
        if (findMovie.getTrailers().isEmpty() || findMovie.getPoster().isEmpty()) {
            trailerUrl = "https://www.kmdb.or.kr/trailer/trailerPlayPop?pFileNm=MK006689_P03.mp4";
        }else {
            trailerUrl = findMovie.getTrailers().get(0).getTrailer_url();
        }
        ArrayList<StillCutDto> stillCutDtos=new ArrayList<>();
        for (int i = 0; i < findMovie.getStillCuts().size(); i++) {
            StillCutDto stillCutDto = new StillCutDto();
            StillCut stillCut = findMovie.getStillCuts().get(i);
            stillCutDto.setStillCut_url(stillCut.getStillCut_url());
            stillCutDtos.add(stillCutDto);
        }

        BoxOfficeMovieDto boxOfficeMovieDto = new BoxOfficeMovieDto();
        boxOfficeMovieDto.movieId= findMovie.getMovieId();
        boxOfficeMovieDto.trailerUrl= trailerUrl;
        boxOfficeMovieDto.posterUrl = findMovie.getPoster();
        boxOfficeMovieDto.rank = boxOfficeMovie.getRank();
        boxOfficeMovieDto.movieNm = boxOfficeMovie.getMovieNm();
        boxOfficeMovieDto.backDrop = findMovie.getBackDrop();
        boxOfficeMovieDto.genre=genreDtos;
        boxOfficeMovieDto.stillCut=stillCutDtos;
        if(findMovie.getPlot().isEmpty()){
            boxOfficeMovieDto.plot="줄거리가 없습니다.";
        } else boxOfficeMovieDto.plot = findMovie.getPlot();

        return boxOfficeMovieDto;
    }
}
