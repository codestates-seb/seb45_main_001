package com.sundayCinema.sundayCinema.movie.api.TMDB;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class MovieSearchResult {
    @JsonProperty("id")
    private int id;
    @JsonProperty("title")
    private String title;
    private String overview;
    @JsonProperty("poster_path")
    private String posterPath;
    @JsonProperty("backdrop_path")
    private String backdropPath;
    private int collectionId; // 컬렉션 ID를 추가
    private String collectionImageUrl; // 컬렉션 이미지 URL을 추가

}