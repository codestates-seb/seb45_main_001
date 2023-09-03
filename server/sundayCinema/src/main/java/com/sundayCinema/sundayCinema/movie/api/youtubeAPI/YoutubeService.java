package com.sundayCinema.sundayCinema.movie.api.youtubeAPI;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class YoutubeService {
    @Value("${YOUTUBE_API_KEY}")
    private String youtubeApiKey;
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    public List<String> getMovieReview(String movieName) throws GeneralSecurityException, IOException {
        YouTube youtubeService = getService();

        YouTube.Search.List searchRequest = youtubeService.search().list(Collections.singletonList("id"));
        searchRequest.setKey(youtubeApiKey);
        String query = movieName + " 리뷰";
        searchRequest.setQ(query);  // 검색어 설정
        searchRequest.setRegionCode("KR");
        searchRequest.setTopicId("/m/02vxn");
        searchRequest.setType(Collections.singletonList("video"));
        searchRequest.setVideoDuration("medium");
        searchRequest.setVideoEmbeddable("true");
        searchRequest.setOrder("relevance");//관련성 높은 순

        SearchListResponse response = searchRequest.execute();
        List<SearchResult> searchResults = response.getItems();

        List<String> videoIds = new ArrayList<>();

        if (searchResults != null && !searchResults.isEmpty()) {
            int maxResults = Math.min(3, searchResults.size()); // 최대 3개 영상 가져오도록 설정

            for (int i = 0; i < maxResults; i++) {
                String videoId = searchResults.get(i).getId().getVideoId();
                videoIds.add(videoId);
            }
        }

        return videoIds;
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
