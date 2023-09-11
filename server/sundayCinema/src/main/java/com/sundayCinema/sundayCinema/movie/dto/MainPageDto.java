package com.sundayCinema.sundayCinema.movie.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MainPageDto {
    private List<BoxOfficeMovieDto> boxofficeList;
    private List<GenreMovieDto> genreMovieList;
}
