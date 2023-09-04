package com.sundayCinema.sundayCinema.movie.mapper;

import com.sundayCinema.sundayCinema.movie.dto.BoxOfficeMovieDto;
import com.sundayCinema.sundayCinema.movie.entity.BoxOfficeMovie;
import com.sundayCinema.sundayCinema.movie.entity.Poster;
import org.springframework.stereotype.Component;

@Component
public class BoxOfficeMovieMapper {
    public BoxOfficeMovieDto boxOfficeResponseDto(BoxOfficeMovie boxOfficeMovie, Poster poster) {
        if (boxOfficeMovie == null && poster == null) {
            return null;
        }
        BoxOfficeMovieDto boxOfficeMovieDto = new BoxOfficeMovieDto();
        boxOfficeMovieDto.posterUrl = poster.getPoster_image_url();
        boxOfficeMovieDto.rank = boxOfficeMovie.getRank();
        boxOfficeMovieDto.movieNm = boxOfficeMovie.getMovieNm();

        return boxOfficeMovieDto;
    }
}
