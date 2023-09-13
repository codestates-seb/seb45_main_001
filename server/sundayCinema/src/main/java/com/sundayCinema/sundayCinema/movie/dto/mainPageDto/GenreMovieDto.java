package com.sundayCinema.sundayCinema.movie.dto.mainPageDto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GenreMovieDto {

    public String genre;
    public long movieId;
    public String movieNm;
    public String posterUrl;
    public String trailerUrl;
    public List<StillCutDto> stillCut;
    public String plot;
    public String backDrop;
}
