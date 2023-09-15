package com.sundayCinema.sundayCinema.movie.mapper;

import com.sundayCinema.sundayCinema.movie.dto.ActorDto;
import com.sundayCinema.sundayCinema.movie.dto.BoxOfficeMovieDto;
import com.sundayCinema.sundayCinema.movie.dto.GenreDto;
import com.sundayCinema.sundayCinema.movie.dto.StillCutDto;
import com.sundayCinema.sundayCinema.movie.entity.boxOffice.BoxOfficeMovie;
import com.sundayCinema.sundayCinema.movie.entity.boxOffice.ForeignBoxOffice;
import com.sundayCinema.sundayCinema.movie.entity.boxOffice.KoreaBoxOffice;
import com.sundayCinema.sundayCinema.movie.entity.movieInfo.Actor;
import com.sundayCinema.sundayCinema.movie.entity.movieInfo.Genre;
import com.sundayCinema.sundayCinema.movie.entity.movieInfo.Movie;
import com.sundayCinema.sundayCinema.movie.entity.movieMedia.StillCut;
import com.sundayCinema.sundayCinema.movie.repository.movieInfoRepo.MovieRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class BoxOfficeMovieMapper {

    public BoxOfficeMovieDto boxOfficeResponseDto(BoxOfficeMovie boxOfficeMovie) {
        ArrayList<GenreDto> genreDtos = new ArrayList<>();
        if (boxOfficeMovie == null) {
            return null;
        }

        Movie findMovie = boxOfficeMovie.getMovie();
        for (int i = 0; i < findMovie.getGenres().size(); i++) {
            GenreDto genreDto = new GenreDto();
            Genre genre = findMovie.getGenres().get(i);
            genreDto.setGenreNm(genre.getGenreNm());
            genreDtos.add(genreDto);
        }

        ArrayList<StillCutDto> stillCutDtos=new ArrayList<>();
        for (int i = 0; i < findMovie.getStillCuts().size(); i++) {
            StillCutDto stillCutDto = new StillCutDto();
            StillCut stillCut = findMovie.getStillCuts().get(i);
            stillCutDto.setStillCut_url(stillCut.getStillCut_url());
            stillCutDtos.add(stillCutDto);
        }

        BoxOfficeMovieDto boxOfficeMovieDto = new BoxOfficeMovieDto();
        boxOfficeMovieDto.movieId= findMovie.getMovieId();
        boxOfficeMovieDto.trailerUrl= findMovie.getTrailers().get(0).getTrailer_url();
        boxOfficeMovieDto.posterUrl = findMovie.getPoster().getPoster_image_url();
        boxOfficeMovieDto.rank = boxOfficeMovie.getRank();
        boxOfficeMovieDto.movieNm = boxOfficeMovie.getMovieNm();
        boxOfficeMovieDto.genre=genreDtos;
        boxOfficeMovieDto.stillCut=stillCutDtos;
        boxOfficeMovieDto.backDrop= findMovie.getBackDrop().getBackDrop_image_url();
        boxOfficeMovieDto.plot= findMovie.getPlot().getPlotText();

        return boxOfficeMovieDto;
    }
    public BoxOfficeMovieDto boxOfficeResponseDto(KoreaBoxOffice boxOfficeMovie) {
        ArrayList<GenreDto> genreDtos = new ArrayList<>();
        if (boxOfficeMovie == null) {
            return null;
        }
        Movie findMovie = boxOfficeMovie.getMovie();
        for (int i = 0; i < findMovie.getGenres().size(); i++) {
            GenreDto genreDto = new GenreDto();
            Genre genre = findMovie.getGenres().get(i);
            genreDto.setGenreNm(genre.getGenreNm());
            genreDtos.add(genreDto);
        }

        ArrayList<StillCutDto> stillCutDtos=new ArrayList<>();
        for (int i = 0; i < findMovie.getStillCuts().size(); i++) {
            StillCutDto stillCutDto = new StillCutDto();
            StillCut stillCut = findMovie.getStillCuts().get(i);
            stillCutDto.setStillCut_url(stillCut.getStillCut_url());
            stillCutDtos.add(stillCutDto);
        }

        BoxOfficeMovieDto boxOfficeMovieDto = new BoxOfficeMovieDto();
        boxOfficeMovieDto.movieId= findMovie.getMovieId();
        boxOfficeMovieDto.trailerUrl= findMovie.getTrailers().get(0).getTrailer_url();
        boxOfficeMovieDto.posterUrl = findMovie.getPoster().getPoster_image_url();
        boxOfficeMovieDto.rank = boxOfficeMovie.getRank();
        boxOfficeMovieDto.movieNm = boxOfficeMovie.getMovieNm();
        boxOfficeMovieDto.genre=genreDtos;
        boxOfficeMovieDto.stillCut=stillCutDtos;
        boxOfficeMovieDto.backDrop= findMovie.getBackDrop().getBackDrop_image_url();
        boxOfficeMovieDto.plot= findMovie.getPlot().getPlotText();

        return boxOfficeMovieDto;
    }
    public BoxOfficeMovieDto boxOfficeResponseDto(ForeignBoxOffice boxOfficeMovie) {
        ArrayList<GenreDto> genreDtos = new ArrayList<>();
        if (boxOfficeMovie == null) {
            return null;
        }

        Movie findMovie = boxOfficeMovie.getMovie();
        for (int i = 0; i < findMovie.getGenres().size(); i++) {
            GenreDto genreDto = new GenreDto();
            Genre genre = findMovie.getGenres().get(i);
            genreDto.setGenreNm(genre.getGenreNm());
            genreDtos.add(genreDto);
        }


        ArrayList<StillCutDto> stillCutDtos=new ArrayList<>();
        for (int i = 0; i < findMovie.getStillCuts().size(); i++) {
            StillCutDto stillCutDto = new StillCutDto();
            StillCut stillCut = findMovie.getStillCuts().get(i);
            stillCutDto.setStillCut_url(stillCut.getStillCut_url());
            stillCutDtos.add(stillCutDto);
        }

        BoxOfficeMovieDto boxOfficeMovieDto = new BoxOfficeMovieDto();
        boxOfficeMovieDto.movieId= findMovie.getMovieId();
        boxOfficeMovieDto.trailerUrl= findMovie.getTrailers().get(0).getTrailer_url();
        boxOfficeMovieDto.posterUrl = findMovie.getPoster().getPoster_image_url();
        boxOfficeMovieDto.rank = boxOfficeMovie.getRank();
        boxOfficeMovieDto.movieNm = boxOfficeMovie.getMovieNm();
        boxOfficeMovieDto.genre=genreDtos;
        boxOfficeMovieDto.stillCut=stillCutDtos;
        boxOfficeMovieDto.backDrop=findMovie.getBackDrop().getBackDrop_image_url();
        boxOfficeMovieDto.plot=findMovie.getPlot().getPlotText();

        return boxOfficeMovieDto;
    }
}
