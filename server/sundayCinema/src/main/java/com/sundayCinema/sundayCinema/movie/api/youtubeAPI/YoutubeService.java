package com.sundayCinema.sundayCinema.movie.api.youtubeAPI;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.sundayCinema.sundayCinema.movie.entity.YoutubeReview;
import com.sundayCinema.sundayCinema.movie.repository.MovieRepository;
import com.sundayCinema.sundayCinema.movie.repository.YoutubeReviewRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
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

    public void getMovieReview(String movieNm) throws GeneralSecurityException, IOException {
        YouTube youtubeService = getService();

        YouTube.Search.List searchRequest = youtubeService.search().list(Collections.singletonList("id"));
        searchRequest.setKey(youtubeApiKey);
        String query = movieNm + " 리뷰";
        searchRequest.setQ(query);  // 검색어 설정
        searchRequest.setRegionCode("KR");
        searchRequest.setTopicId("/m/02vxn");
        searchRequest.setType(Collections.singletonList("video"));
        searchRequest.setVideoDuration("medium");
        searchRequest.setVideoEmbeddable("true");
        searchRequest.setOrder("relevance");//관련성 높은 순

        SearchListResponse response = searchRequest.execute();
        List<SearchResult> searchResults = response.getItems();
        log.info(String.valueOf(searchResults.get(0)));


        if (searchResults != null && !searchResults.isEmpty()) {
            int maxResults = Math.min(3, searchResults.size()); // 최대 3개 영상 가져오도록 설정

            for (int i = 0; i < maxResults; i++) {
                SearchResult searchResult = searchResults.get(i);

                String videoId = searchResult.getId().getVideoId();
//                String thumbnailUrl = searchResult.getSnippet().getThumbnails().getDefault().getUrl();
//                String channelId = searchResult.getSnippet().getChannelId();
//                log.info(thumbnailUrl);
//                log.info(channelId);
                YoutubeReview review = new YoutubeReview();
                if (youtubeReviewRepository.findMaxReviewId() == null) {
                    review.setReviewId(0);

                } else {
                    long youtubeId = youtubeReviewRepository.findMaxReviewId();
                    review.setReviewId(youtubeId+1);
                }
                review.setYoutubeReview_url(videoId);
//                    review.setYoutubeChannel(channelId);
//                    review.setThumbnail(thumbnailUrl);
                review.setMovie(movieRepository.findByMovieNm(movieNm));
                youtubeReviewRepository.save(review);
            }
        }
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
