package com.sundayCinema.sundayCinema.movie.api.apiResponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TmdbSearchResults {
    @JsonProperty("results")
    private List<TmdbMovieSearchResult> results;

    public List<TmdbMovieSearchResult> getResults() {
        return results;
    }

    public void setResults(List<TmdbMovieSearchResult> results) {
        this.results = results;
    }
}