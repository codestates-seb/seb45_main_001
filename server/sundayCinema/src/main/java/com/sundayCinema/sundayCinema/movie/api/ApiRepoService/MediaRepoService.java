package com.sundayCinema.sundayCinema.movie.api.ApiRepoService;

import com.google.api.services.youtube.model.SearchResult;
import com.sundayCinema.sundayCinema.movie.api.KMDB.KdmbResponse;
import com.sundayCinema.sundayCinema.movie.api.KMDB.KdmbService;
import com.sundayCinema.sundayCinema.movie.api.TMDB.MovieDetails;
import com.sundayCinema.sundayCinema.movie.api.TMDB.TmdbService;
import com.sundayCinema.sundayCinema.movie.api.youtubeAPI.YoutubeService;
import com.sundayCinema.sundayCinema.movie.entity.boxOffice.BoxOfficeMovie;
import com.sundayCinema.sundayCinema.movie.entity.movieInfo.Movie;
import com.sundayCinema.sundayCinema.movie.entity.movieMedia.*;
import com.sundayCinema.sundayCinema.movie.repository.movieInfoRepo.MovieRepository;
import com.sundayCinema.sundayCinema.movie.repository.movieMediaRepo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@Service
@Slf4j
public class MediaRepoService {

    private final TmdbService tmdbService;
    private final KdmbService kdmbService;
    private final YoutubeService youtubeService;
    private final PosterRepository posterRepository;
    private final PlotRepository plotRepository;
    private final TrailerRepository trailerRepository;
    private final BackDropRepository backDropRepository;
    private final YoutubeReviewRepository youtubeReviewRepository;
    private final StillCutRepository stillCutRepository;


    public MediaRepoService(TmdbService tmdbService, KdmbService kdmbService, YoutubeService youtubeService,
                            PosterRepository posterRepository, PlotRepository plotRepository,
                            TrailerRepository trailerRepository, BackDropRepository backDropRepository,
                            YoutubeReviewRepository youtubeReviewRepository,
                            StillCutRepository stillCutRepository) {
        this.tmdbService = tmdbService;
        this.kdmbService = kdmbService;
        this.youtubeService = youtubeService;
        this.posterRepository = posterRepository;
        this.plotRepository = plotRepository;
        this.trailerRepository = trailerRepository;
        this.backDropRepository = backDropRepository;
        this.youtubeReviewRepository = youtubeReviewRepository;
        this.stillCutRepository = stillCutRepository;
    }

    //배경화면 저장
    public void saveBackDrop(List<BoxOfficeMovie> boxList) {
        for (int i = 0; i < boxList.size(); i++) {
            BoxOfficeMovie findBox = boxList.get(i);
            if (backDropRepository.existsByMovie(findBox.getMovie())) {
            } // 영화 객체를 통해 배경화면 객체가 존재하면 그냥 넘어감
            else {
                MovieDetails findMovieDetails = tmdbService.getMovieDetailsByTitle(findBox);

                BackDrop backDrop = new BackDrop();
                backDrop.setBackDrop_image_url(findMovieDetails.getBackdropPath());
                backDrop.setMovie(boxList.get(i).getMovie());       // 영화 매핑
                backDropRepository.save(backDrop);
            }
        }
    }

    public void savePoster(List<BoxOfficeMovie> boxList) throws IOException {
        for (int i = 0; i < boxList.size(); i++) {
            BoxOfficeMovie findBox = boxList.get(i);
            if (posterRepository.existsByMovie(findBox.getMovie())) {
            } else {
                MovieDetails findMovieDetails = tmdbService.getMovieDetailsByTitle(findBox);
                posterSaveFilter(findBox, findMovieDetails);
            }
        }
    }

    public void savePlot(List<BoxOfficeMovie> boxList) throws IOException {
        for (int i = 0; i < boxList.size(); i++) {
            BoxOfficeMovie findBox = boxList.get(i);
            if (plotRepository.existsByMovie(findBox.getMovie())) {
            } else {
                MovieDetails findMovieDetails = tmdbService.getMovieDetailsByTitle(findBox);
                plotSaveFilter(findBox, findMovieDetails);
            }
        }
    }

    // 트레일러 저장
    public void saveTrailer(List<BoxOfficeMovie> boxList) throws IOException, GeneralSecurityException {
        for (int i = 0; i < boxList.size(); i++) {
            BoxOfficeMovie findBox = boxList.get(i);
            if (trailerRepository.existsByMovie(findBox.getMovie())) {
            } else {
                MovieDetails findMovieDetails = tmdbService.getMovieDetailsByTitle(findBox);
                trailerSaveFilter(findBox, findMovieDetails);
            }
        }
    }


    public void saveStill(List<BoxOfficeMovie> boxList) throws IOException {
        for (BoxOfficeMovie boxOfficeMovie : boxList) {
            if (stillCutRepository.existsByMovie(boxOfficeMovie.getMovie())) {
            } else {
                KdmbResponse kdmbResponse = kdmbService.generateKdmb(boxOfficeMovie);
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
                            stillCutUrl = "https://call.nts.go.kr/images/ap/cm/img_coming.png";
                        }
                        stillCut.setStillCut_url(stillCutUrl);
                        stillCut.setMovie(boxOfficeMovie.getMovie());
                        stillCutRepository.save(stillCut);
                    }
                }
            }
        }
    }

    public void saveYoutubeReview(List<BoxOfficeMovie> boxList) throws GeneralSecurityException, IOException {
        for (int i = 0; i < boxList.size(); i++) {
            if (youtubeReviewRepository.existsByMovie(boxList.get(i).getMovie())) {
            } else {
                String movieName = boxList.get(i).getMovieNm();
                List<SearchResult> searchReview = youtubeService.searchYoutube(movieName, "리뷰");
                youtubeService.saveYoutube(searchReview, movieName);
            }
        }
    }
    public void saveReview(List<Movie> movieList) throws GeneralSecurityException, IOException {
        for (int i = 0; i < movieList.size(); i++) {
            if (youtubeReviewRepository.existsByMovie(movieList.get(i))) {
            } else {
                String movieName = movieList.get(i).getMovieNm();
                List<SearchResult> searchReview = youtubeService.searchYoutube(movieName, "리뷰");
                youtubeService.saveYoutube(searchReview, movieName);
            }
        }
    }
    public void saveKmdbPoster(KdmbResponse kdmbResponse, Poster poster) {
        if (kdmbResponse != null && kdmbResponse.getData() != null
                && !kdmbResponse.getData().isEmpty() && kdmbResponse.getData().get(0).getResult() != null
                && !kdmbResponse.getData().get(0).getResult().isEmpty()) {
                String posterUrls = kdmbResponse.getData().get(0).getResult().get(0).getPosters(); // 포스터 주소 추출
            if (posterUrls != null) {
                String[] posterArray = posterUrls.split("\\|");
                String posterImageUrl = posterArray[0];
                poster.setPoster_image_url(posterImageUrl);
                posterRepository.save(poster);
            }
        }else{
            poster.setPoster_image_url("https://call.nts.go.kr/images/ap/cm/img_coming.png");
            posterRepository.save(poster);
        }
    }

    public void saveKmdbPlot(KdmbResponse kdmbResponse, Plot plot) {
        if (kdmbResponse != null && kdmbResponse.getData() != null
                && !kdmbResponse.getData().isEmpty() && kdmbResponse.getData().get(0).getResult() != null
                && !kdmbResponse.getData().get(0).getResult().isEmpty()
                && kdmbResponse.getData().get(0).getResult().get(0).getPlots() != null
                && kdmbResponse.getData().get(0).getResult().get(0).getPlots().getPlot() != null
                && !kdmbResponse.getData().get(0).getResult().get(0).getPlots().getPlot().isEmpty()) {

            String plotText = kdmbResponse.getData().get(0).getResult().get(0).getPlots().getPlot().get(0).getPlotText();

            plot.setPlotText(plotText);
            plotRepository.save(plot);
        }else {
            plot.setPlotText("준비중입니다");
            plotRepository.save(plot);
        }
    }

    //예외 필터 플롯 포스터 메서드
    public void posterSaveFilter(BoxOfficeMovie boxOfficeMovie, MovieDetails movieDetails) throws IOException {
        Poster poster = new Poster();
        poster.setPoster_image_url(movieDetails.getPosterPath());
        poster.setMovie(boxOfficeMovie.getMovie());
        if (movieDetails.getTitle().equals(boxOfficeMovie.getMovieNm())) { // 입력한 영화 제목 == 검색 결과 영화제목
            if (movieDetails.getTitle().equals("기본값")) { //TMDB가 제대로 된 검색을 못 했을 때
                //kdmb 검색
                KdmbResponse kdmbResponse = kdmbService.generateKdmb(boxOfficeMovie);
                saveKmdbPoster(kdmbResponse, poster);
            } else {
                // TMDB 저장 메서드 호출
                posterRepository.save(poster);
            }
        } else {
            //kdmb 검색
            KdmbResponse kdmbResponse = kdmbService.generateKdmb(boxOfficeMovie);
            saveKmdbPoster(kdmbResponse, poster);
        }
    }

    public void plotSaveFilter(BoxOfficeMovie boxOfficeMovie, MovieDetails movieDetails) throws IOException {
        Plot plot = new Plot();
        plot.setPlotText(movieDetails.getOverview());
        plot.setMovie(boxOfficeMovie.getMovie());
        if (movieDetails.getTitle().equals(boxOfficeMovie.getMovieNm())) { // 입력한 영화 제목 == 검색 결과 영화제목
            if (movieDetails.getTitle().equals("기본값")) { //TMDB가 제대로 된 검색을 못 했을 때
                //kdmb 검색
                KdmbResponse kdmbResponse = kdmbService.generateKdmb(boxOfficeMovie);
                saveKmdbPlot(kdmbResponse, plot);
            } else {
                // TMDB 저장 메서드 호출
                plotRepository.save(plot);
            }
        } else {
            //kdmb 검색
            KdmbResponse kdmbResponse = kdmbService.generateKdmb(boxOfficeMovie);
            saveKmdbPlot(kdmbResponse, plot);
        }
    }

    //예외 필터 트레일러 메서드
    public void trailerSaveFilter(BoxOfficeMovie boxOfficeMovie, MovieDetails movieDetails)
            throws IOException, GeneralSecurityException {
        Trailer trailer = new Trailer();
        trailer.setTrailer_url(movieDetails.getKey());
        trailer.setMovie(boxOfficeMovie.getMovie());
        if (movieDetails.getTitle().equals(boxOfficeMovie.getMovieNm())) { // 입력한 영화 제목 == 검색 결과 영화제목
            if (movieDetails.getTitle().equals("기본값")) { //TMDB가 제대로 된 검색을 못 했을 때
                //youtube 검색
                List<SearchResult> findYoutubeResult = youtubeService.searchYoutube(boxOfficeMovie.getMovieNm(), "공식 예고편");
                if (findYoutubeResult != null && !findYoutubeResult.isEmpty() &&
                        findYoutubeResult.get(0).getId() != null && findYoutubeResult.get(0).getId().getVideoId() != null) {

                    String youtubeVodId = findYoutubeResult.get(0).getId().getVideoId();
                    trailer.setTrailer_url(youtubeVodId);

                } else {
                    trailer.setTrailer_url("mXZZvpTvtIQ");
                }
                trailerRepository.save(trailer);
            }
        } else {
            //youtube 검색
            List<SearchResult> findYoutubeResult = youtubeService.searchYoutube(boxOfficeMovie.getMovieNm(), "공식 예고편");
            if (findYoutubeResult != null && !findYoutubeResult.isEmpty() &&
                    findYoutubeResult.get(0).getId() != null && findYoutubeResult.get(0).getId().getVideoId() != null) {

                String youtubeVodId = findYoutubeResult.get(0).getId().getVideoId();
                trailer.setTrailer_url(youtubeVodId);

            } else
                trailer.setTrailer_url("mXZZvpTvtIQ");
        }
        trailerRepository.save(trailer);
    }
    }

