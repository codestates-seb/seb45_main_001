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

    @JsonProperty("belongs_to_collection")
    private CollectionInfo collectionInfo;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    @Setter
    public static class CollectionInfo {
        @JsonProperty("id")
        private int id;
    }
}