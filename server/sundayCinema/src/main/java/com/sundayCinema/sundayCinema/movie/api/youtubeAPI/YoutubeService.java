package com.sundayCinema.sundayCinema.movie.api.youtubeAPI;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.sundayCinema.sundayCinema.movie.entity.movieMedia.YoutubeReview;
import com.sundayCinema.sundayCinema.movie.repository.movieInfoRepo.MovieRepository;
import com.sundayCinema.sundayCinema.movie.repository.movieMediaRepo.YoutubeReviewRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class YoutubeService {
    @Value("${YOUTUBE_API_KEY}")
    private String youtubeApiKey;
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    private final YoutubeReviewRepository youtubeReviewRepository;

    private final MovieRepository movieRepository;

    public YoutubeService(YoutubeReviewRepository youtubeReviewRepository, MovieRepository movieRepository) {
        this.youtubeReviewRepository = youtubeReviewRepository;
        this.movieRepository = movieRepository;
    }

    public List<SearchResult> searchYoutube(String movieNm, String keyword) throws GeneralSecurityException, IOException {
        YouTube youtubeService = getService();

        YouTube.Search.List searchRequest = youtubeService.search().list(Collections.singletonList("id, snippet"));
        searchRequest.setKey(youtubeApiKey);
        String query = movieNm + " " + keyword;
        searchRequest.setQ(query);  // 검색어 설정
        searchRequest.setRegionCode("KR");
        searchRequest.setTopicId("/m/02vxn");
        searchRequest.setType(Collections.singletonList("video"));
//        searchRequest.setVideoDuration("medium");
        searchRequest.setVideoEmbeddable("true");
        searchRequest.setOrder("relevance");//관련성 높은 순
        log.info(query);
        SearchListResponse response = searchRequest.execute();
        List<SearchResult> searchResults = response.getItems();

        return searchResults;
    }

    public void saveYoutube(List<SearchResult> findYoutubeResult, String movieNm) {

                YoutubeReview review = new YoutubeReview();
                if(findYoutubeResult.get(0).getId().getVideoId()==null){
                    review.setYoutubeReview_url("mXZZvpTvtIQ");
                }else {
                    review.setYoutubeReview_url(findYoutubeResult.get(0).getId().getVideoId());
                }

                review.setMovie(movieRepository.findByMovieNm(movieNm));
                youtubeReviewRepository.save(review);
    }

    private YouTube getService() throws GeneralSecurityException, IOException {
        return new YouTube.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                null
        )
                .setApplicationName("SundayCinema")
                .build();
    }
}
