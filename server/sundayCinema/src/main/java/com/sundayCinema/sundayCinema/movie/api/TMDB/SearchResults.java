package com.sundayCinema.sundayCinema.movie.api.TMDB;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchResults {
    @JsonProperty("results")
    private List<MovieSearchResult> results;

    public List<MovieSearchResult> getResults() {
        return results;
    }

    public void setResults(List<MovieSearchResult> results) {
        this.results = results;
    }
}