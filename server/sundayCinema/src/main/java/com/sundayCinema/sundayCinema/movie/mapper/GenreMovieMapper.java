package com.sundayCinema.sundayCinema.movie.mapper;

import com.sundayCinema.sundayCinema.movie.dto.mainPageDto.GenreMovieDto;
import com.sundayCinema.sundayCinema.movie.dto.mainPageDto.StillCutDto;
import com.sundayCinema.sundayCinema.movie.entity.movieInfo.Movie;
import com.sundayCinema.sundayCinema.movie.entity.movieMedia.StillCut;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@Slf4j
public class GenreMovieMapper {
    public GenreMovieDto responseGenreMovieDto(Movie movie, String genreNm){
        String poster ="";
        String trailer="";
        if(movie.getPoster().isEmpty()){
            poster= "http://file.koreafilm.or.kr/thm/02/99/18/12/tn_DPF027618.jpg";
        }else{poster= movie.getPoster();}
        if(movie.getTrailers().isEmpty()){
            trailer= "https://www.kmdb.or.kr/trailer/trailerPlayPop?pFileNm=MK006689_P03.mp4";
        }else{trailer= movie.getTrailers().get(0).getTrailer_url();}
        GenreMovieDto genreMovieDto = new GenreMovieDto();

        ArrayList<StillCutDto> stillCutDtos=new ArrayList<>();
        for (int i = 0; i < movie.getStillCuts().size(); i++) {
            StillCutDto stillCutDto = new StillCutDto();
            StillCut stillCut = movie.getStillCuts().get(i);
            stillCutDto.setStillCut_url(stillCut.getStillCut_url());
            stillCutDtos.add(stillCutDto);
        }
        genreMovieDto.movieId = movie.getMovieId();
        genreMovieDto.movieNm = movie.getMovieNm();
        genreMovieDto.posterUrl = poster;
        genreMovieDto.genre = genreNm;
        genreMovieDto.trailerUrl =trailer;
        genreMovieDto.backDrop = movie.getBackDrop();
        if(movie.getPlot().isEmpty()){
            genreMovieDto.plot="줄거리가 없습니다.";
        }
        else genreMovieDto.plot = movie.getPlot();
        genreMovieDto.stillCut= stillCutDtos;
        return genreMovieDto;
    }
}
