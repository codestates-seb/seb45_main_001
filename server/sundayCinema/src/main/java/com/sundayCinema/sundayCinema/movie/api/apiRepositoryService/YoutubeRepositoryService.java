package com.sundayCinema.sundayCinema.movie.api.apiRepositoryService;

import com.google.api.services.youtube.model.SearchResult;
import com.sundayCinema.sundayCinema.movie.entity.movieInfo.Movie;
import com.sundayCinema.sundayCinema.movie.entity.movieMedia.YoutubeEntity;
import com.sundayCinema.sundayCinema.movie.repository.movieMediaRepo.YoutubeEntityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class YoutubeRepositoryService {
    private final YoutubeEntityRepository youtubeEntityRepository;

    public YoutubeRepositoryService(YoutubeEntityRepository youtubeEntityRepository) {
        this.youtubeEntityRepository = youtubeEntityRepository;
    }

    public void saveYoutube(List<SearchResult> searchResults, Movie movie) {
        if (searchResults != null && !searchResults.isEmpty()) {

            int maxResults = Math.min(3, searchResults.size()); // 최대 3개 영상 가져오도록 설정

            for (int i = 0; i < maxResults; i++) {
                YoutubeEntity youtubeResponse = new YoutubeEntity();
                SearchResult searchResult = searchResults.get(i);
                String youtubeVod_url = "https://www.youtube.com/watch?v=" + Optional.ofNullable(searchResult.getId())
                        .map(id -> id.getVideoId())
                        .orElse("준비중입니다.");

                String thumbnailUrl = Optional.ofNullable(searchResult.getSnippet())
                        .map(snippet -> snippet.getThumbnails())
                        .map(thumbnails -> thumbnails.getDefault())
                        .map(defaultThumbnail -> defaultThumbnail.getUrl())
                        .orElse("기본 이미지 URL 없음");

                String youtubeChannel = "https://www.youtube.com/channel/" + Optional.ofNullable(searchResult.getSnippet())
                        .map(snippet -> snippet.getChannelId())
                        .orElse("채널 ID 없음");

                Long maxYoutubeId= youtubeEntityRepository.findMaxYoutubeId();
                if(maxYoutubeId != null){
                    youtubeResponse.setYoutubeId(maxYoutubeId+i+1);
                }else {
                    youtubeResponse.setYoutubeId(0);
                }
                youtubeResponse.setYoutubeChannel(youtubeChannel);
                youtubeResponse.setThumbnail(thumbnailUrl);
                youtubeResponse.setYoutubeVod_url(youtubeVod_url);
                youtubeResponse.setMovie(movie);
                youtubeEntityRepository.save(youtubeResponse);
            }
        }
    }
}
