package com.sundayCinema.sundayCinema.movie.mapper;

import com.sundayCinema.sundayCinema.movie.dto.detaiPagelDto.*;
import com.sundayCinema.sundayCinema.movie.entity.boxOffice.BoxOfficeMovie;
import com.sundayCinema.sundayCinema.movie.entity.movieInfo.Actor;
import com.sundayCinema.sundayCinema.movie.entity.movieInfo.Movie;
import com.sundayCinema.sundayCinema.movie.entity.movieMedia.StillCut;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Instanceof;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class MovieDetailsMapper {
    public DetailsBasicInfo detailsBasicResponseDto(Movie movie) {
        DetailsBasicInfo detailsBasicInfo = new DetailsBasicInfo();

        List<String> nationList = new ArrayList<>();
        List<String> genreList = new ArrayList<>();
        for (int i = 0; i < movie.getNations().size(); i++) {
                nationList.add(movie.getNations().get(i).getNationNm());
        }
        for (int i = 0; i < movie.getGenres().size(); i++) {
                genreList.add(movie.getGenres().get(i).getGenreNm());

        }

        ArrayList<StillCutDto> stillCutDtos=new ArrayList<>();
        for (int i = 0; i < movie.getStillCuts().size(); i++) {
            StillCutDto stillCutDto = new StillCutDto();
            StillCut stillCut = movie.getStillCuts().get(i);
            stillCutDto.setStillCut_url(stillCut.getStillCut_url());
            stillCutDtos.add(stillCutDto);
        }
        /*
        무비 -> 얘가 어떤 박스 오피스에 있는가?

        찾은 박스오피스 객체에서 값을 가져온다.
         */
        String audiAcc="";
        String openDt="";
        String rank="";

        if(movie.getBoxOfficeMovie() != null){
            audiAcc=movie.getBoxOfficeMovie().getAudiAcc();
            openDt=movie.getBoxOfficeMovie().getOpenDt();
            rank=movie.getBoxOfficeMovie().getRank();
        }else if(movie.getKoreaBoxOfficeMovie() !=null){
            audiAcc=movie.getKoreaBoxOfficeMovie().getAudiAcc();
            openDt=movie.getKoreaBoxOfficeMovie().getOpenDt();
            rank=movie.getKoreaBoxOfficeMovie().getRank();
        }else if(movie.getForeignBoxOffice() !=null){
            audiAcc=movie.getForeignBoxOffice().getAudiAcc();
            openDt=movie.getForeignBoxOffice().getOpenDt();
            rank=movie.getForeignBoxOffice().getRank();
        }else if(movie.getGenreBoxOfficeMovie() !=null){
            audiAcc=movie.getGenreBoxOfficeMovie().getAudiAcc();
            openDt=movie.getGenreBoxOfficeMovie().getOpenDt();
            rank=movie.getGenreBoxOfficeMovie().getRank();
        }else {
            audiAcc="...";
            openDt="...";
            rank="...";
        }

        detailsBasicInfo.movieId = movie.getMovieId();
        detailsBasicInfo.movieNm = movie.getMovieNm();
        detailsBasicInfo.movieNmEn = movie.getMovieNmEn();
        detailsBasicInfo.audiAcc = audiAcc;
        detailsBasicInfo.openDt = openDt;
        detailsBasicInfo.rank = rank;
        detailsBasicInfo.watchGradeNm = movie.getAudits().get(0).getWatchGradeNm();
        detailsBasicInfo.nation = nationList;
        detailsBasicInfo.genre = genreList;
        detailsBasicInfo.plot = movie.getPlot().getPlotText();
        detailsBasicInfo.poster = movie.getPoster().getPoster_image_url();
        detailsBasicInfo.stillCut = stillCutDtos;
        detailsBasicInfo.backDrop = movie.getBackDrop().getBackDrop_image_url();
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

        detailsMainInfo.director = directorList;
        detailsMainInfo.actors = actorList;
        detailsMainInfo.plot = movie.getPlot().getPlotText();
        return detailsMainInfo;
    }

    public DetailsMediaInfo detailsMediaInfoResponseDto(Movie movie) {
        DetailsMediaInfo detailsMediaInfo = new DetailsMediaInfo();
        ArrayList<StillCutDto> stillCutDtos=new ArrayList<>();

        for (int i = 0; i < movie.getStillCuts().size(); i++) {
            StillCutDto stillCutDto = new StillCutDto();
            StillCut stillCut = movie.getStillCuts().get(i);
            stillCutDto.setStillCut_url(stillCut.getStillCut_url());
            stillCutDtos.add(stillCutDto);
        }
        detailsMediaInfo.stillCuts = stillCutDtos;
        detailsMediaInfo.youtubeReviews = movie.getYoutubeReviews().get(0).getYoutubeReview_url();
        detailsMediaInfo.trailers = movie.getTrailers().get(0).getTrailer_url();

        return detailsMediaInfo;
    }

}
