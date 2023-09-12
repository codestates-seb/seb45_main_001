package com.sundayCinema.sundayCinema.movie.dto;

import com.sundayCinema.sundayCinema.movie.entity.movieMedia.StillCut;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BoxOfficeMovieDto {
    public long movieId;
    public String posterUrl;
    public List<StillCutDto> stillCut;
    public String plot;
    public String rank;
    public String movieNm;
    public List<GenreDto> genre;
    public String trailerUrl;
    // 메인 예고편
    // 영화 플롯
}
