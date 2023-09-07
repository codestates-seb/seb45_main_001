package com.sundayCinema.sundayCinema.movie.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoxOfficeMovieDto {
    public long movieId;
    public String posterUrl;
    public String rank;
    public String movieNm;

    // 장르 리스트 형태
    // 메인 예고편
    // 영화 플롯
}
