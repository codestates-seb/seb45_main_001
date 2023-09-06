package com.sundayCinema.sundayCinema.movie.mapper;

import com.sundayCinema.sundayCinema.movie.dto.mainPage.BoxOfficeMovieDto;
import com.sundayCinema.sundayCinema.movie.entity.boxofficeMovie.BoxOfficeMovie;
import com.sundayCinema.sundayCinema.movie.entity.boxofficeMovie.ForeignBoxOffice;
import com.sundayCinema.sundayCinema.movie.entity.boxofficeMovie.KoreaBoxOffice;
import com.sundayCinema.sundayCinema.movie.entity.movieInfo.Movie;
import com.sundayCinema.sundayCinema.movie.repository.movieInfoRepo.MovieRepository;
import org.springframework.stereotype.Component;

@Component
public class BoxOfficeMovieMapper {
    private final MovieRepository movieRepository;

    public BoxOfficeMovieMapper(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

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
