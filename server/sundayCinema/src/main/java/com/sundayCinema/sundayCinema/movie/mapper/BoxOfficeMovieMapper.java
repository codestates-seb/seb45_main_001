package com.sundayCinema.sundayCinema.movie.mapper;

import com.sundayCinema.sundayCinema.movie.dto.ActorDto;
import com.sundayCinema.sundayCinema.movie.dto.BoxOfficeMovieDto;
import com.sundayCinema.sundayCinema.movie.dto.GenreDto;
import com.sundayCinema.sundayCinema.movie.dto.StillCutDto;
import com.sundayCinema.sundayCinema.movie.entity.boxOffice.BoxOfficeMovie;
import com.sundayCinema.sundayCinema.movie.entity.boxOffice.ForeignBoxOffice;
import com.sundayCinema.sundayCinema.movie.entity.boxOffice.KoreaBoxOffice;
import com.sundayCinema.sundayCinema.movie.entity.movieInfo.Actor;
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
        String posterUrl="";
        if (findMovie.getTrailers().isEmpty() || findMovie.getPosters().isEmpty()) {
             trailerUrl = "http://file.koreafilm.or.kr/thm/02/99/18/17/tn_DPF027860.jpg";
             posterUrl = "https://www.kmdb.or.kr/trailer/trailerPlayPop?pFileNm=MK006689_P03.mp4";
        }else {
            trailerUrl = findMovie.getTrailers().get(0).getTrailer_url();
            posterUrl = findMovie.getPosters().get(0).getPoster_image_url();
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
        boxOfficeMovieDto.posterUrl = posterUrl;
        boxOfficeMovieDto.rank = boxOfficeMovie.getRank();
        boxOfficeMovieDto.movieNm = boxOfficeMovie.getMovieNm();
        boxOfficeMovieDto.genre=genreDtos;
        boxOfficeMovieDto.stillCut=stillCutDtos;
        if(findMovie.getPlots().isEmpty()||findMovie.getPlots().get(0)==null){
            boxOfficeMovieDto.plot="줄거리가 없습니다.";
        }
        boxOfficeMovieDto.plot = findMovie.getPlots().get(0).getPlotText();
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
        String posterUrl="";
        if (findMovie.getTrailers().isEmpty() || findMovie.getPosters().isEmpty()) {
            trailerUrl = "http://file.koreafilm.or.kr/thm/02/99/18/17/tn_DPF027860.jpg";
            posterUrl = "https://www.kmdb.or.kr/trailer/trailerPlayPop?pFileNm=MK006689_P03.mp4";
        }else {
            trailerUrl = findMovie.getTrailers().get(0).getTrailer_url();
            posterUrl = findMovie.getPosters().get(0).getPoster_image_url();
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
        boxOfficeMovieDto.posterUrl = posterUrl;
        boxOfficeMovieDto.rank = boxOfficeMovie.getRank();
        boxOfficeMovieDto.movieNm = boxOfficeMovie.getMovieNm();
        boxOfficeMovieDto.genre=genreDtos;
        boxOfficeMovieDto.stillCut=stillCutDtos;
        if(findMovie.getPlots().isEmpty()||findMovie.getPlots().get(0)==null){
            boxOfficeMovieDto.plot="줄거리가 없습니다.";
        }else boxOfficeMovieDto.plot = findMovie.getPlots().get(0).getPlotText();

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
        String posterUrl="";
        if (findMovie.getTrailers().isEmpty() || findMovie.getPosters().isEmpty()) {
            trailerUrl = "http://file.koreafilm.or.kr/thm/02/99/18/17/tn_DPF027860.jpg";
            posterUrl = "https://www.kmdb.or.kr/trailer/trailerPlayPop?pFileNm=MK006689_P03.mp4";
        }else {
            trailerUrl = findMovie.getTrailers().get(0).getTrailer_url();
            posterUrl = findMovie.getPosters().get(0).getPoster_image_url();
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
        boxOfficeMovieDto.posterUrl = posterUrl;
        boxOfficeMovieDto.rank = boxOfficeMovie.getRank();
        boxOfficeMovieDto.movieNm = boxOfficeMovie.getMovieNm();
        boxOfficeMovieDto.genre=genreDtos;
        boxOfficeMovieDto.stillCut=stillCutDtos;
        if(findMovie.getPlots().isEmpty()||findMovie.getPlots().get(0)==null){
            boxOfficeMovieDto.plot="줄거리가 없습니다.";
        } else boxOfficeMovieDto.plot = findMovie.getPlots().get(0).getPlotText();

        return boxOfficeMovieDto;
    }
}
