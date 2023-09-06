package com.sundayCinema.sundayCinema.movie.mapper;

import com.sundayCinema.sundayCinema.movie.dto.detailPage.DetailsBasicInfo;
import com.sundayCinema.sundayCinema.movie.entity.boxofficeMovie.BoxOfficeMovie;
import com.sundayCinema.sundayCinema.movie.entity.movieInfo.Movie;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MovieDetailsMapper {
    public DetailsBasicInfo movieDetailsBasicResponseDto(BoxOfficeMovie boxOfficeMovie, Movie movie){
        DetailsBasicInfo detailsBasicInfo= new DetailsBasicInfo();
        String nationList = "";
        String genreList ="";
        List<String> posterList = new ArrayList<>(movie.getPosters().size());
        for(int i=0; i < movie.getNations().size(); i++) {
            if(i==0){nationList=movie.getNations().get(i).getNationNm(); }
            else nationList += "/"+ movie.getNations().get(i).getNationNm();
        }
        for(int i=0; i < movie.getGenres().size(); i++) {
            if(i==0){genreList=movie.getGenres().get(i).getGenreNm(); }
            else genreList += "/"+ movie.getGenres().get(i).getGenreNm();
        }
        for(int i=0; i< movie.getPosters().size();i++){
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
}
