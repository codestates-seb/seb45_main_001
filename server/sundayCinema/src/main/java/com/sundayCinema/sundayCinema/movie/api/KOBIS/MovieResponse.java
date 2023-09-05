package com.sundayCinema.sundayCinema.movie.api.KOBIS;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sundayCinema.sundayCinema.movie.entity.Movie;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class MovieResponse {
    @JsonProperty("movieInfoResult")
    private MovieItem movieInfoResult;

    @Getter
    @Setter
    public static class MovieItem{
        @JsonProperty("movieInfo")
        Movie movieInfo;
        @JsonProperty("source")
        String source;
    }
}
