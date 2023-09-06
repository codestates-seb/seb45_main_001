package com.sundayCinema.sundayCinema.movie.dto.mainPage;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoxOfficeMovieDto {
    public long movieId;
    public String posterUrl;
    public String rank;
    public String movieNm;

}
