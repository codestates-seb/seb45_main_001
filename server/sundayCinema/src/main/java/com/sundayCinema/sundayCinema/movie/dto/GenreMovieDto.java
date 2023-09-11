package com.sundayCinema.sundayCinema.movie.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
public class GenreMovieDto {

    public String genre;
    public String movieNm;
    public String posterUrl;
    public String trailerUrl;
}
