package com.sundayCinema.sundayCinema.movie.api.apiResponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class TmdbMovieDetails {
    @JsonProperty("id")
    private int id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("backdrop_path")
    private String backdropPath;

    private String overview;

    @JsonProperty("poster_path")
    private String posterPath;


}