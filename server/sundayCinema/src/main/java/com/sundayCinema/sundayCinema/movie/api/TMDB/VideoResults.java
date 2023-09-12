package com.sundayCinema.sundayCinema.movie.api.TMDB;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VideoResults {
    @JsonProperty("results")
    private List<VideoInfo> results;

    public List<VideoInfo> getResults() {
        return results;
    }

    public void setResults(List<VideoInfo> results) {
        this.results = results;
    }
}