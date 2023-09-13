package com.sundayCinema.sundayCinema.movie.api.apiService;

import com.sundayCinema.sundayCinema.movie.api.apiResponse.TmdbMovieDetails;
import com.sundayCinema.sundayCinema.movie.api.apiResponse.TmdbSearchResults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class TmdbApiService {
    private final String apiKey = "ccba510dce22da81439817bcf25e5121"; // application.properties에서 읽어올 수도 있음
    private final String tmdbBaseUrl = "https://api.themoviedb.org/3";
    private final RestTemplate restTemplate = new RestTemplate();


    public TmdbMovieDetails getMovieDetailsByTitle(String movieTitle) {
        String searchUrl = tmdbBaseUrl + "/search/movie?api_key=" + apiKey + "&language=ko-KR&query=" + movieTitle;

        TmdbSearchResults tmdbSearchResults = restTemplate.getForObject(searchUrl, TmdbSearchResults.class);

        if (tmdbSearchResults != null && tmdbSearchResults.getResults() != null && !tmdbSearchResults.getResults().isEmpty()) {
            int movieId = tmdbSearchResults.getResults().get(0).getId();
            String movieDetailsUrl = tmdbBaseUrl + "/movie/" + movieId + "?api_key=" + apiKey + "&language=ko-KR";
            TmdbMovieDetails tmdbMovieDetails = restTemplate.getForObject(movieDetailsUrl, TmdbMovieDetails.class);

            if (tmdbMovieDetails != null) {
                if(tmdbMovieDetails.getBackdropPath()==null){
                    tmdbMovieDetails.setBackdropPath("배경이 없습니다");
                }else tmdbMovieDetails.setBackdropPath(makeAbsoluteImageUrl(tmdbMovieDetails.getBackdropPath()));
                if(tmdbMovieDetails.getPosterPath()==null){
                    tmdbMovieDetails.setPosterPath("포스터가 없습니다");
                }else tmdbMovieDetails.setPosterPath(makeAbsoluteImageUrl(tmdbMovieDetails.getPosterPath()));
            }

            return tmdbMovieDetails;
        } else {
            TmdbMovieDetails tmdbMovieDetails = new TmdbMovieDetails();
            tmdbMovieDetails.setTitle("정보가 없습니다");
            tmdbMovieDetails.setBackdropPath("배경이 없습니다");
            tmdbMovieDetails.setPosterPath("포스터가 없습니다");
            return tmdbMovieDetails;
        }
    }

    private String makeAbsoluteImageUrl(String relativeImageUrl) {
        if (relativeImageUrl != null && !relativeImageUrl.isEmpty()) {
            return "https://image.tmdb.org/t/p/w780" + relativeImageUrl;
        }
        return null;
    }
}