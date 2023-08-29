package com.sundayCinema.sundayCinema.movie.openAPI;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenApiResponse {
    @JsonProperty("movieListResult")
    private MovieListResult movieListResult;

    public MovieListResult getMovieListResult() {
        return movieListResult;
    }

    public void setMovieListResult(MovieListResult movieListResult) {
        this.movieListResult = movieListResult;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MovieListResult {

        @JsonProperty("movieList")
        private List<MovieInfo> movieList;

        public List<MovieInfo> getMovieList() {
            return movieList;
        }

        public void setMovieList(List<MovieInfo> movieList) {
            this.movieList = movieList;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MovieInfo {

        @JsonProperty("movieNm")
        private String movieName;

        // 다른 필요한 정보들도 추가할 수 있습니다.

        public String getMovieName() {
            return movieName;
        }

        public void setMovieName(String movieName) {
            this.movieName = movieName;
        }
    }
}
