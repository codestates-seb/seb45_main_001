package com.sundayCinema.sundayCinema.movie.mapper;

import com.sundayCinema.sundayCinema.movie.dto.BoxOfficeMovieDto;
import com.sundayCinema.sundayCinema.movie.entity.boxOffice.BoxOfficeMovie;
import com.sundayCinema.sundayCinema.movie.entity.boxOffice.ForeignBoxOffice;
import com.sundayCinema.sundayCinema.movie.entity.boxOffice.KoreaBoxOffice;
import com.sundayCinema.sundayCinema.movie.entity.movieInfo.Movie;
import com.sundayCinema.sundayCinema.movie.repository.movieInfoRepo.MovieRepository;
import org.springframework.stereotype.Component;

@Component
public class BoxOfficeMovieMapper {
    private final MovieRepository movieRepository;

    public BoxOfficeMovieMapper(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    //todo : boxoffice id로 받을지 말지 고민하기


    public BoxOfficeMovieDto boxOfficeResponseDto(BoxOfficeMovie boxOfficeMovie) {
        if (boxOfficeMovie == null) {
            return null;
        }
        String movieCd= boxOfficeMovie.getMovieCd();
        Movie findMovie = movieRepository.findByMovieCd(movieCd);

        BoxOfficeMovieDto boxOfficeMovieDto = new BoxOfficeMovieDto();
        boxOfficeMovieDto.movieId= findMovie.getMovieId();
        boxOfficeMovieDto.posterUrl = findMovie.getPosters().get(0).getPoster_image_url();
        boxOfficeMovieDto.rank = boxOfficeMovie.getRank();
        boxOfficeMovieDto.movieNm = boxOfficeMovie.getMovieNm();

        return boxOfficeMovieDto;
    }
    public BoxOfficeMovieDto boxOfficeResponseDto(KoreaBoxOffice boxOfficeMovie) {
        if (boxOfficeMovie == null) {
            return null;
        }
        String movieCd= boxOfficeMovie.getMovieCd();
        Movie findMovie = movieRepository.findByMovieCd(movieCd);

        BoxOfficeMovieDto boxOfficeMovieDto = new BoxOfficeMovieDto();
        boxOfficeMovieDto.movieId= findMovie.getMovieId();
        boxOfficeMovieDto.posterUrl = findMovie.getPosters().get(0).getPoster_image_url();
        boxOfficeMovieDto.rank = boxOfficeMovie.getRank();
        boxOfficeMovieDto.movieNm = boxOfficeMovie.getMovieNm();

        return boxOfficeMovieDto;
    }
    public BoxOfficeMovieDto boxOfficeResponseDto(ForeignBoxOffice boxOfficeMovie) {
        if (boxOfficeMovie == null) {
            return null;
        }
        String movieCd= boxOfficeMovie.getMovieCd();
        Movie findMovie = movieRepository.findByMovieCd(movieCd);

        BoxOfficeMovieDto boxOfficeMovieDto = new BoxOfficeMovieDto();
        boxOfficeMovieDto.movieId= findMovie.getMovieId();
        boxOfficeMovieDto.posterUrl = findMovie.getPosters().get(0).getPoster_image_url();
        boxOfficeMovieDto.rank = boxOfficeMovie.getRank();
        boxOfficeMovieDto.movieNm = boxOfficeMovie.getMovieNm();

        return boxOfficeMovieDto;
    }

}
