package com.sundayCinema.sundayCinema.movie.api.ApiRepoService;

import com.sundayCinema.sundayCinema.movie.api.KMDB.KdmbResponse;
import com.sundayCinema.sundayCinema.movie.api.KMDB.KdmbService;
import com.sundayCinema.sundayCinema.movie.api.TMDB.MovieDetails;
import com.sundayCinema.sundayCinema.movie.api.TMDB.TmdbService;
import com.sundayCinema.sundayCinema.movie.api.youtubeAPI.YoutubeService;
import com.sundayCinema.sundayCinema.movie.entity.boxOffice.BoxOfficeMovie;
import com.sundayCinema.sundayCinema.movie.entity.movieMedia.BackDrop;
import com.sundayCinema.sundayCinema.movie.entity.movieMedia.Plot;
import com.sundayCinema.sundayCinema.movie.entity.movieMedia.Poster;
import com.sundayCinema.sundayCinema.movie.entity.movieMedia.Trailer;
import com.sundayCinema.sundayCinema.movie.repository.movieMediaRepo.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@Service
public class MediaRepoService {

    private final KobisRepoService kobisRepoService;
    private final TmdbService tmdbService;
    private final KdmbService kdmbService;
    private final YoutubeService youtubeService;
    private final PosterRepository posterRepository;
    private final PlotRepository plotRepository;
    private final TrailerRepository trailerRepository;
    private final BackDropRepository backDropRepository;
    private final YoutubeReviewRepository youtubeReviewRepository;
    private final StillCutRepository stillCutRepository;

    public MediaRepoService(KobisRepoService kobisRepoService, TmdbService tmdbService, KdmbService kdmbService,
                            YoutubeService youtubeService, PosterRepository posterRepository, PlotRepository plotRepository,
                            TrailerRepository trailerRepository, BackDropRepository backDropRepository,
                            YoutubeReviewRepository youtubeReviewRepository, StillCutRepository stillCutRepository) {
        this.kobisRepoService = kobisRepoService;
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
    public void saveBackDrop(List<BoxOfficeMovie>boxList) {
        for (int i = 0; i < boxList.size(); i++) {
            BoxOfficeMovie findBox = boxList.get(i);
            if (backDropRepository.existsByMovie(findBox.getMovie())) {} // 영화 객체를 통해 배경화면 객체가 존재하면 그냥 넘어감
            else {
                MovieDetails findMovieDetails = tmdbService.getMovieDetailsByTitle(findBox);

                BackDrop backDrop = new BackDrop();
                backDrop.setBackDrop_image_url(findMovieDetails.getBackdropPath());
                backDrop.setMovie(boxList.get(i).getMovie());       // 영화 매핑
                backDropRepository.save(backDrop);
            }
        }
    }

    public void savePoster(List<BoxOfficeMovie>boxList) throws IOException {
        for (int i = 0; i < boxList.size(); i++) {
            BoxOfficeMovie findBox = boxList.get(i);
            if (posterRepository.existsByMovie(findBox.getMovie())) {
            } else {
                MovieDetails findMovieDetails = tmdbService.getMovieDetailsByTitle(findBox);
                posterSaveFilter(findBox, findMovieDetails);
            }
        }
    }

    public void savePlot(List<BoxOfficeMovie>boxList) throws IOException {
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
    public void saveTrailer(List<BoxOfficeMovie>boxList) throws IOException, GeneralSecurityException {
        for (int i = 0; i < boxList.size(); i++) {
            BoxOfficeMovie findBox = boxList.get(i);
            if (trailerRepository.existsByMovie(findBox.getMovie())) {
            } else {
                MovieDetails findMovieDetails = tmdbService.getMovieDetailsByTitle(findBox);
                trailerSaveFilter(findBox, findMovieDetails);
            }
        }
    }

    //예외 필터 플롯 포스터 메서드
    public void posterSaveFilter(BoxOfficeMovie boxOfficeMovie, MovieDetails movieDetails) throws IOException {
        Poster poster = new Poster();
        poster.setPoster_image_url(movieDetails.getPosterPath());
        poster.setMovie(boxOfficeMovie.getMovie());
        if(movieDetails.getTitle().equals(boxOfficeMovie.getMovieNm())){ // 입력한 영화 제목 == 검색 결과 영화제목
            if(movieDetails.getTitle().equals("기본값")){ //TMDB가 제대로 된 검색을 못 했을 때
                //kdmb 검색
                KdmbResponse kdmbResponse= kdmbService.generateKdmb(boxOfficeMovie);
                kdmbService.savePoster(kdmbResponse, poster);
            }
            else{
                // TMDB 저장 메서드 호출
                posterRepository.save(poster);
            }
        }else{
            //kdmb 검색
            KdmbResponse kdmbResponse= kdmbService.generateKdmb(boxOfficeMovie);
            kdmbService.savePoster(kdmbResponse, poster);
        }
    }
    public void plotSaveFilter(BoxOfficeMovie boxOfficeMovie, MovieDetails movieDetails) throws IOException {
        Plot plot = new Plot();
        plot.setPlotText(movieDetails.getOverview());
        plot.setMovie(boxOfficeMovie.getMovie());
        if(movieDetails.getTitle().equals(boxOfficeMovie.getMovieNm())){ // 입력한 영화 제목 == 검색 결과 영화제목
            if(movieDetails.getTitle().equals("기본값")){ //TMDB가 제대로 된 검색을 못 했을 때
                //kdmb 검색
                KdmbResponse kdmbResponse= kdmbService.generateKdmb(boxOfficeMovie);
                kdmbService.savePlot(kdmbResponse, plot);
            }
            else{
                // TMDB 저장 메서드 호출
                plotRepository.save(plot);
            }
        }else{
            //kdmb 검색
            KdmbResponse kdmbResponse= kdmbService.generateKdmb(boxOfficeMovie);
            kdmbService.savePlot(kdmbResponse, plot);
        }
    }

    //예외 필터 트레일러 메서드
    public void trailerSaveFilter(BoxOfficeMovie boxOfficeMovie, MovieDetails movieDetails) throws IOException, GeneralSecurityException {
        Trailer trailer = new Trailer();
        trailer.setTrailer_url(movieDetails.getKey());
        trailer.setMovie(boxOfficeMovie.getMovie());
        if(movieDetails.getTitle().equals(boxOfficeMovie.getMovieNm())){ // 입력한 영화 제목 == 검색 결과 영화제목
            if(movieDetails.getTitle().equals("기본값")){ //TMDB가 제대로 된 검색을 못 했을 때
                //youtube 검색
             String youtubeVodId=  youtubeService.searchYoutube(boxOfficeMovie.getMovieNm(),"공식 예고편")
                     .get(0).getId().getVideoId();
             trailer.setTrailer_url(youtubeVodId);
             trailerRepository.save(trailer);
            }
            else{
                // TMDB 저장 메서드 호출
                trailerRepository.save(trailer);
            }
        }else{
            //youtube 검색
            String youtubeVodId=  youtubeService.searchYoutube(boxOfficeMovie.getMovieNm(),"공식 예고편")
                    .get(0).getId().getVideoId();
            trailer.setTrailer_url(youtubeVodId);
            trailerRepository.save(trailer);
        }
    }
}
