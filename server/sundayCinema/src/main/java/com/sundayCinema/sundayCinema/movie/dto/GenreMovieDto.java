package com.sundayCinema.sundayCinema.movie.dto;

import com.sundayCinema.sundayCinema.movie.entity.movieMedia.StillCut;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

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
}
