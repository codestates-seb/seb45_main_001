package com.sundayCinema.sundayCinema.movie.dto;

import com.sundayCinema.sundayCinema.movie.entity.movieInfo.Genre;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BoxOfficeMovieDto {
    public long movieId;
    public String posterUrl;
    public String rank;
    public String movieNm;
    public List<GenreDto> genre;
    // 메인 예고편
    // 영화 플롯
}
