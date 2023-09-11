package com.sundayCinema.sundayCinema.movie.mapper;

import com.sundayCinema.sundayCinema.movie.dto.ActorDto;
import com.sundayCinema.sundayCinema.movie.dto.BoxOfficeMovieDto;
import com.sundayCinema.sundayCinema.movie.dto.GenreDto;
import com.sundayCinema.sundayCinema.movie.entity.boxOffice.BoxOfficeMovie;
import com.sundayCinema.sundayCinema.movie.entity.boxOffice.ForeignBoxOffice;
import com.sundayCinema.sundayCinema.movie.entity.boxOffice.KoreaBoxOffice;
import com.sundayCinema.sundayCinema.movie.entity.movieInfo.Actor;
import com.sundayCinema.sundayCinema.movie.entity.movieInfo.Genre;
import com.sundayCinema.sundayCinema.movie.entity.movieInfo.Movie;
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
        BoxOfficeMovieDto boxOfficeMovieDto = new BoxOfficeMovieDto();
        boxOfficeMovieDto.movieId= findMovie.getMovieId();
        boxOfficeMovieDto.posterUrl = findMovie.getPosters().get(0).getPoster_image_url();
        boxOfficeMovieDto.rank = boxOfficeMovie.getRank();
        boxOfficeMovieDto.movieNm = boxOfficeMovie.getMovieNm();
        boxOfficeMovieDto.genre=genreDtos;
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
        BoxOfficeMovieDto boxOfficeMovieDto = new BoxOfficeMovieDto();
        boxOfficeMovieDto.movieId= findMovie.getMovieId();
        if(findMovie.getPosters().isEmpty()){boxOfficeMovieDto.posterUrl="http://file.koreafilm.or.kr/thm/02/99/18/12/tn_DPF027618.jpg";}
        else boxOfficeMovieDto.posterUrl = findMovie.getPosters().get(0).getPoster_image_url();
        boxOfficeMovieDto.rank = boxOfficeMovie.getRank();
        boxOfficeMovieDto.movieNm = boxOfficeMovie.getMovieNm();
        boxOfficeMovieDto.genre=genreDtos;

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
        BoxOfficeMovieDto boxOfficeMovieDto = new BoxOfficeMovieDto();
        boxOfficeMovieDto.movieId= findMovie.getMovieId();
        boxOfficeMovieDto.posterUrl = findMovie.getPosters().get(0).getPoster_image_url();
        boxOfficeMovieDto.rank = boxOfficeMovie.getRank();
        boxOfficeMovieDto.movieNm = boxOfficeMovie.getMovieNm();
        boxOfficeMovieDto.genre=genreDtos;

        return boxOfficeMovieDto;
    }

}
