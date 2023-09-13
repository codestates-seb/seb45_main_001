package com.sundayCinema.sundayCinema.movie.api.apiRepositoryService;

import com.google.api.services.youtube.model.SearchResult;
import com.sundayCinema.sundayCinema.movie.api.apiResponse.KdmbResponse;
import com.sundayCinema.sundayCinema.movie.api.apiService.YoutubeApiService;
import com.sundayCinema.sundayCinema.movie.entity.movieInfo.Movie;
import com.sundayCinema.sundayCinema.movie.entity.movieMedia.StillCut;
import com.sundayCinema.sundayCinema.movie.entity.movieMedia.Trailer;
import com.sundayCinema.sundayCinema.movie.repository.movieInfoRepo.MovieRepository;
import com.sundayCinema.sundayCinema.movie.repository.movieMediaRepo.StillCutRepository;
import com.sundayCinema.sundayCinema.movie.repository.movieMediaRepo.TrailerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class KdmbRepositoryService {
    private final StillCutRepository stillCutRepository;
    private final TrailerRepository trailerRepository;
    private final MovieRepository movieRepository;

    private final YoutubeApiService youtubeApiService;

    public KdmbRepositoryService(StillCutRepository stillCutRepository, TrailerRepository trailerRepository,
                                 MovieRepository movieRepository,
                                 YoutubeApiService youtubeApiService) {
        this.stillCutRepository = stillCutRepository;
        this.trailerRepository = trailerRepository;
        this.movieRepository = movieRepository;
        this.youtubeApiService = youtubeApiService;
    }

    public void saveKdmb(KdmbResponse kdmbResponse, Movie movie) throws GeneralSecurityException, IOException {
        saveStill(kdmbResponse, movie);
        saveTrailer(kdmbResponse, movie);
    }

    public void saveStill(KdmbResponse kdmbResponse, Movie movie) {
        if (kdmbResponse != null && kdmbResponse.getData() != null
                && !kdmbResponse.getData().isEmpty() && kdmbResponse.getData().get(0).getResult() != null
                && !kdmbResponse.getData().get(0).getResult().isEmpty()) {
            String stillUrls = kdmbResponse.getData().get(0).getResult().get(0).getStlls();
            String[] stillArray = stillUrls.split("\\|");
            for (int i = 0; i < stillArray.length; i++) {
                StillCut stillCut = new StillCut();
                if (stillCutRepository.findMaxStillCutId() == null) {
                    stillCut.setStillCutId(0);
                } else {
                    stillCut.setStillCutId(stillCutRepository.findMaxStillCutId() + 1);
                }

                String stillCutUrl = stillArray[i];

                // 스틸컷 URL이 비어있을 경우 기본 URL 또는 다른 처리를 할 수 있습니다.
                if (stillCutUrl == null || stillCutUrl.isEmpty()) {
                    stillCutUrl = movie.getPoster();
                    log.info("poster"+stillCutUrl);
                }

                stillCut.setStillCut_url(stillCutUrl);
                stillCut.setMovie(movie);
                stillCutRepository.save(stillCut);
            }
        }
    }

    public void saveTrailer(KdmbResponse kdmbResponse, Movie movie) throws GeneralSecurityException, IOException {
        if (kdmbResponse != null && kdmbResponse.getData() != null
                && !kdmbResponse.getData().isEmpty() && kdmbResponse.getData().get(0).getResult() != null
                && !kdmbResponse.getData().get(0).getResult().isEmpty()) {
            List<KdmbResponse.Vod.VodInfo> vods = kdmbResponse.getData().get(0).getResult().get(0).getVods().getVod();

            for (int i = 0; i < vods.size(); i++) {
                String vodUrl = vods.get(i).getVodUrl();
                String vodClass = vods.get(i).getVodClass();
                Trailer trailer = new Trailer();
                if (trailerRepository.findMaxTrailerId() == null) {
                    trailer.setTrailerId(0);
                } else {
                    trailer.setTrailerId(trailerRepository.findMaxTrailerId() + 1);
                }

                // 트레일러 URL이 비어있을 경우
                if (vodUrl == null || vodUrl.isEmpty()) {
                    String movieName = movie.getMovieNm();
                    SearchResult searchResult = youtubeApiService.searchYoutube(movieName, "메인 예고편").get(0);
                    String searchYoutubeTrailer =
                            "https://www.youtube.com/watch?v=" + Optional.ofNullable(searchResult.getId())
                                    .map(id -> id.getVideoId())
                                    .orElse("준비중입니다.");


                    trailer.setMovie(movie);
                    trailer.setTrailer_url(searchYoutubeTrailer);
                    trailerRepository.save(trailer);
                }

                trailer.setVodClass(vodClass);
                trailer.setTrailer_url(vodUrl);
                trailer.setMovie(movie);
                trailerRepository.save(trailer);
            }
        }
    }

//    public void savePlot(KdmbResponse kdmbResponse, String movieCd) {
//        if (kdmbResponse != null && kdmbResponse.getData() != null
//                && !kdmbResponse.getData().isEmpty() && kdmbResponse.getData().get(0).getResult() != null
//                && !kdmbResponse.getData().get(0).getResult().isEmpty()
//                && kdmbResponse.getData().get(0).getResult().get(0).getPlots() != null
//                && kdmbResponse.getData().get(0).getResult().get(0).getPlots().getPlot() != null
//                && !kdmbResponse.getData().get(0).getResult().get(0).getPlots().getPlot().isEmpty()) {
//
//            String plotText = kdmbResponse.getData().get(0).getResult().get(0).getPlots().getPlot().get(0).getPlotText();
//            // 플롯 텍스트가 비어있는지 확인
//            if (plotText == null || plotText.isEmpty()) {
//                plotText = "플롯 텍스트가 없습니다."; // 플롯 텍스트가 없을 경우 기본 메시지 설정
//            }
//
//            Plots plots = new Plots();
//            if (plotRepository.findMaxPlotId() == null) {
//                plots.setPlotId(0);
//            } else {
//                plots.setPlotId(plotRepository.findMaxPlotId() + 1);
//            }
//            plots.setPlotText(plotText);
//            plots.setMovie(movieRepository.findByMovieCd(movieCd));
//            plotRepository.save(plots);
//        }
//    }

    //    public void savePoster(KdmbResponse kdmbResponse, String movieCd) {
//        if (kdmbResponse != null && kdmbResponse.getData() != null
//                && !kdmbResponse.getData().isEmpty() && kdmbResponse.getData().get(0).getResult() != null
//                && !kdmbResponse.getData().get(0).getResult().isEmpty()) {
//            String posterUrls = kdmbResponse.getData().get(0).getResult().get(0).getPosters();
//            if (posterUrls != null) {
//                String[] posterArray = posterUrls.split("\\|");
//                for (int i = 0; i < posterArray.length; i++) {
//                    Poster poster = new Poster();
//                    log.info("poster : " + poster.getPoster_image_url());
//                    if (posterRepository.findMaxPosterId() == null) {
//                        poster.setPosterId(0);
//                    } else {
//                        poster.setPosterId(posterRepository.findMaxPosterId() + 1);
//                    }
//
//                    String posterImageUrl = posterArray[i];
//
//                    // 포스터 이미지 URL이 비어있을 경우 기본 URL 또는 다른 처리를 할 수 있습니다.
//                    if (posterImageUrl == null || posterImageUrl.isEmpty()) {
//                        posterImageUrl = "기본 포스터 이미지 URL"; // 기본 URL 또는 다른 처리 추가
//                    }
//
//                    poster.setPoster_image_url(posterImageUrl);
//                    poster.setMovie(movieRepository.findByMovieCd(movieCd));
//                    posterRepository.save(poster);
//                }
//            }
//        }
//    }

}
