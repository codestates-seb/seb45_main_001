package com.sundayCinema.sundayCinema.movie.api.TMDB;

import com.sundayCinema.sundayCinema.movie.Service.MovieService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;

@Service
@Slf4j
public class TmdbService {
    @Value("${TMDB_API_KEY}")
    private  String apiKey; // application.properties에서 읽어올 수도 있음
    private final String tmdbBaseUrl = "https://api.themoviedb.org/3";
    private final RestTemplate restTemplate = new RestTemplate();

    public MovieDetails getMovieDetailsByTitle(String movieTitle) {
        String searchUrl = tmdbBaseUrl + "/search/movie?api_key=" + apiKey + "&language=ko-KR&query=" + movieTitle;

        SearchResults searchResults = restTemplate.getForObject(searchUrl, SearchResults.class);
        log.debug("검색된 영화 목록1: {}", searchResults.getResults());
        if (searchResults != null && searchResults.getResults() != null && !searchResults.getResults().isEmpty()) {
            int movieId = searchResults.getResults().get(0).getId();
            String movieDetailsUrl = tmdbBaseUrl + "/movie/" + movieId + "?api_key=" + apiKey + "&language=ko-KR";
            MovieDetails movieDetails = restTemplate.getForObject(movieDetailsUrl, MovieDetails.class);
            log.debug("Movie Details1: {}", movieDetails);
//            log.info("movieDetails"+movieDetails.getPosterPath().isEmpty());
            // 배경화면 및 포스터 이미지 URL을 절대 URL로 변환
            if (movieDetails != null) {
                if(movieDetails.getBackdropPath()==null){
                    movieDetails.setBackdropPath("배경화면을 준비중입니다");
                }else  movieDetails.setBackdropPath(makeAbsoluteImageUrl(movieDetails.getBackdropPath()));
                if(movieDetails.getPosterPath()==null){
                    movieDetails.setPosterPath("포스터를 준비중입니다");
                }else movieDetails.setPosterPath(makeAbsoluteImageUrl(movieDetails.getPosterPath()));
            }

            return movieDetails;
        } else {
            MovieDetails movieDetails= new MovieDetails();
            movieDetails.setBackdropPath("배경화면을 준비중입니다");
            return movieDetails;
        }
    }

    // TMDB에서 제공하는 상대 이미지 URL을 절대 URL로 변환
    private String makeAbsoluteImageUrl(String relativeImageUrl) {
        if (relativeImageUrl != null && !relativeImageUrl.isEmpty()) {
            return "https://image.tmdb.org/t/p/w780" + relativeImageUrl;
        }
        return null;
    }

    // 예고편을 가져오는 메서드 추가
    public String getMovieTrailer(String movieTitle) {
        // 영화 제목을 기반으로 검색
        String searchUrl = tmdbBaseUrl + "/search/movie?api_key=" + apiKey + "&language=ko-KR&query=" + movieTitle;

        SearchResults searchResults = restTemplate.getForObject(searchUrl, SearchResults.class);

        if (searchResults != null && searchResults.getResults() != null && !searchResults.getResults().isEmpty()) {
            int movieId = searchResults.getResults().get(0).getId();
            log.debug("검색된 영화 목록2: {}", searchResults.getResults());

            // 검색된 영화의 ID를 사용하여 예고편을 가져오기
            String trailerUrl = tmdbBaseUrl + "/movie/" + movieId + "/videos?api_key=" + apiKey;
            VideoResults videoResults = restTemplate.getForObject(trailerUrl, VideoResults.class);

            if (videoResults != null && videoResults.getResults() != null && !videoResults.getResults().isEmpty()) {
                // 예고편의 첫 번째 동영상 키 반환
                return videoResults.getResults().get(0).getKey();
            }
        }

        return null;
    }
//    public int getCollectionIdByMovieTitle(String movieTitle) throws IOException {
//        // 영화 제목을 기반으로 컬렉션을 검색하는 TMDB API 엔드포인트
//        String searchUrl = tmdbBaseUrl + "/search/movie?api_key=" + apiKey + "&query=" + movieTitle;
//
//        SearchResults searchResults = restTemplate.getForObject(searchUrl, SearchResults.class);
//        log.info("searchResults3 :"+ searchResults.getResults().get(0).toString());
//        if (searchResults != null && searchResults.getResults() != null && !searchResults.getResults().isEmpty()) {
//            // 검색된 첫 번째 영화의 ID를 얻어옵니다.
//            int movieId = searchResults.getResults().get(0).getId();
//            log.debug("검색된 영화 목록3: {}", searchResults.getResults());
//            // 해당 영화의 상세 정보를 가져옵니다.
//            String movieDetailsUrl = tmdbBaseUrl + "/movie/" + movieId + "?api_key=" + apiKey + "&language=ko-KR";
//            MovieDetails movieDetails = restTemplate.getForObject(movieDetailsUrl, MovieDetails.class);
//            log.debug("Movie Details2: {}", movieDetails);
//            // 영화의 컬렉션 ID를 반환합니다. (가져온 정보에 따라서)
//            return movieDetails.getCollectionInfo().getId();
//        } else {
//            return -1; // 검색된 영화 없음을 나타내는 값
//        }
//    }
}