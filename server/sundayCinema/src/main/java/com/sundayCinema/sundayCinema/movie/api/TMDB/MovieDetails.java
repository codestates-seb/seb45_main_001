package com.sundayCinema.sundayCinema.movie.api.TMDB;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class MovieDetails {
    @JsonProperty("id")
    private int id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("backdrop_path")
    private String backdropPath;

    private String overview;

    @JsonProperty("poster_path")
    private String posterPath;

    @JsonProperty("key")
    private String key;  // 예고편 키값을 추가

    // 추가로 필요한 필드들을 정의할 수 있음
}