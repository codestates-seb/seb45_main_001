package com.sundayCinema.sundayCinema.movie.mapper;

import com.sundayCinema.sundayCinema.movie.dto.*;
import com.sundayCinema.sundayCinema.movie.entity.boxOffice.BoxOfficeMovie;
import com.sundayCinema.sundayCinema.movie.entity.movieInfo.Actor;
import com.sundayCinema.sundayCinema.movie.entity.movieInfo.Movie;
import com.sundayCinema.sundayCinema.movie.entity.movieMedia.Trailer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class MovieDetailsMapper {
    public DetailsBasicInfo detailsBasicResponseDto(BoxOfficeMovie boxOfficeMovie, Movie movie) {
        DetailsBasicInfo detailsBasicInfo = new DetailsBasicInfo();
        String nationList = "";
        String genreList = "";
        List<String> posterList = new ArrayList<>(movie.getPosters().size());
        for (int i = 0; i < movie.getNations().size(); i++) {
            if (i == 0) {
                nationList = movie.getNations().get(i).getNationNm();
            } else nationList += "/" + movie.getNations().get(i).getNationNm();
        }
        for (int i = 0; i < movie.getGenres().size(); i++) {
            if (i == 0) {
                genreList = movie.getGenres().get(i).getGenreNm();
            } else genreList += "/" + movie.getGenres().get(i).getGenreNm();
        }
        for (int i = 0; i < movie.getPosters().size(); i++) {
            posterList.add(movie.getPosters().get(i).getPoster_image_url());
        }
        detailsBasicInfo.movieNm = boxOfficeMovie.getMovieNm();
        detailsBasicInfo.audiAcc = boxOfficeMovie.getAudiAcc();
        detailsBasicInfo.openDt = boxOfficeMovie.getOpenDt();
        detailsBasicInfo.rank = boxOfficeMovie.getRank();
        detailsBasicInfo.watchGradeNm = movie.getAudits().get(0).getWatchGradeNm();
        detailsBasicInfo.nation = nationList;
        detailsBasicInfo.genre = genreList;
        detailsBasicInfo.poster = posterList;

        return detailsBasicInfo;
    }

    public DetailsMainInfo detailsMainInfoResponseDto(Movie movie) {
        DetailsMainInfo detailsMainInfo = new DetailsMainInfo();
        ArrayList<ActorDto> actorList = new ArrayList<>();
        String directorList = "";
        for (int i = 0; i < movie.getDirectors().size(); i++) {
            if (i == 0) directorList = movie.getDirectors().get(i).getPeopleNm();
            else directorList += ", " + movie.getDirectors().get(i).getPeopleNm();
        }
        for (int i = 0; i < movie.getActors().size(); i++) {
            ActorDto actorDto = new ActorDto();
            Actor actor = movie.getActors().get(i);
            actorDto.setCast(actor.getCast());
            actorDto.setPeopleNm(actor.getPeopleNm());
            actorList.add(actorDto);
        }
        detailsMainInfo.plot = movie.getPlots().get(0).getPlotText();
        detailsMainInfo.director = directorList;
        detailsMainInfo.actors = actorList;

        return detailsMainInfo;
    }

    public DetailsMediaInfo detailsMediaInfoResponseDto(Movie movie) {
        DetailsMediaInfo detailsMediaInfo = new DetailsMediaInfo();
        List<String> stillCutList = new ArrayList<>();
        List<String> youtubeReviewList = new ArrayList<>();
        List<TrailerDto> trailerList = new ArrayList<>();
            for (int i = 0; i < movie.getStillCuts().size(); i++) {
            stillCutList.add(movie.getStillCuts().get(i).getStillCut_url());
            log.info("stillCutList :" + movie.getStillCuts().get(i).getStillCut_url());
        }
            for (int i = 0; i < movie.getYoutubeReviews().size(); i++) {
            youtubeReviewList.add(movie.getYoutubeReviews().get(i).getYoutubeReview_url());
            log.info("youtubeList : "+ movie.getYoutubeReviews().get(i).getYoutubeReview_url());
        }
            for (int i=0; i< movie.getTrailers().size(); i++){
                TrailerDto trailerDto = new TrailerDto();
                Trailer trailer= movie.getTrailers().get(i);
                trailerDto.setTrailer_url(trailer.getTrailer_url());
                trailerDto.setVodClass(trailer.getVodClass());
                trailerList.add(trailerDto);
                log.info("trailer : "+ trailer.getTrailer_url());
            }
        detailsMediaInfo.stillCuts = stillCutList;
        detailsMediaInfo.youtubeReviews = youtubeReviewList;
        detailsMediaInfo.trailers = trailerList;
        log.info("list" + String.valueOf(stillCutList));
        return detailsMediaInfo;
    }

}
