package com.sundayCinema.sundayCinema.movie.mapper;

import com.sundayCinema.sundayCinema.movie.dto.GenreMovieDto;
import com.sundayCinema.sundayCinema.movie.entity.movieInfo.Movie;
import org.springframework.stereotype.Component;

@Component
public class GenreMovieMapper {
    public GenreMovieDto responseGenreMovieDto(Movie movie, String genreNm){
        String poster ="";
        String trailer="";
        if(movie.getPosters().isEmpty()){
            poster= "http://file.koreafilm.or.kr/thm/02/99/18/12/tn_DPF027618.jpg";
        }else{poster= movie.getPosters().get(0).getPoster_image_url();}
        if(movie.getTrailers().isEmpty()){
            trailer= "https://www.kmdb.or.kr/trailer/trailerPlayPop?pFileNm=MK006689_P03.mp4";
        }else{trailer= movie.getTrailers().get(0).getTrailer_url();}
        GenreMovieDto genreMovieDto = new GenreMovieDto();
        genreMovieDto.movieNm = movie.getMovieNm();
        genreMovieDto.posterUrl = poster;
        genreMovieDto.genre = genreNm;
        genreMovieDto.trailerUrl =trailer;
        return genreMovieDto;
    }
}
