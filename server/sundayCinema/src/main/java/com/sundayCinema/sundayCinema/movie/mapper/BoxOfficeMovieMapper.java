package com.sundayCinema.sundayCinema.movie.mapper;

import com.sundayCinema.sundayCinema.movie.dto.mainPageDto.BoxOfficeMovieDto;
import com.sundayCinema.sundayCinema.movie.dto.mainPageDto.GenreDto;
import com.sundayCinema.sundayCinema.movie.dto.detaiPagelDto.StillCutDto;
import com.sundayCinema.sundayCinema.movie.entity.boxOffice.BoxOfficeMovie;
import com.sundayCinema.sundayCinema.movie.entity.boxOffice.ForeignBoxOffice;
import com.sundayCinema.sundayCinema.movie.entity.boxOffice.KoreaBoxOffice;
import com.sundayCinema.sundayCinema.movie.entity.movieInfo.Genre;
import com.sundayCinema.sundayCinema.movie.entity.movieInfo.Movie;
import com.sundayCinema.sundayCinema.movie.entity.movieMedia.StillCut;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BoxOfficeMovieMapper {
    public BoxOfficeMovieDto boxOfficeResponseDto(BoxOfficeMovie boxOfficeMovie) {
        if (boxOfficeMovie == null) {
            return null;
        }
        return mapToDto(boxOfficeMovie.getMovie(), boxOfficeMovie.getRank(), boxOfficeMovie.getMovieNm());
    }

    public BoxOfficeMovieDto boxOfficeResponseDto(KoreaBoxOffice boxOfficeMovie) {
        if (boxOfficeMovie == null) {
            return null;
        }
        return mapToDto(boxOfficeMovie.getMovie(), boxOfficeMovie.getRank(), boxOfficeMovie.getMovieNm());
    }

    public BoxOfficeMovieDto boxOfficeResponseDto(ForeignBoxOffice boxOfficeMovie) {
        if (boxOfficeMovie == null) {
            return null;
        }
        return mapToDto(boxOfficeMovie.getMovie(), boxOfficeMovie.getRank(), boxOfficeMovie.getMovieNm());
    }

    private BoxOfficeMovieDto mapToDto(Movie movie, String rank, String movieNm) {
        // 공통 매핑 로직을 수행
        ArrayList<GenreDto> genreDtos = mapGenres(movie.getGenres());
        ArrayList<StillCutDto> stillCutDtos = mapStillCuts(movie.getStillCuts());

        BoxOfficeMovieDto boxOfficeMovieDto = new BoxOfficeMovieDto();
        boxOfficeMovieDto.movieId = movie.getMovieId();
        boxOfficeMovieDto.trailerUrl = movie.getTrailers().get(0).getTrailer_url();
        boxOfficeMovieDto.posterUrl = movie.getPoster().getPoster_image_url();
        boxOfficeMovieDto.rank = rank;
        boxOfficeMovieDto.movieNm = movieNm;
        boxOfficeMovieDto.genre = genreDtos;
        boxOfficeMovieDto.stillCut = stillCutDtos;
        boxOfficeMovieDto.backDrop = movie.getBackDrop().getBackDrop_image_url();
        boxOfficeMovieDto.plot = movie.getPlot().getPlotText();

        return boxOfficeMovieDto;
    }

    private ArrayList<GenreDto> mapGenres(List<Genre> genres) {
        ArrayList<GenreDto> genreDtos = new ArrayList<>();
        for (Genre genre : genres) {
            GenreDto genreDto = new GenreDto();
            genreDto.setGenreNm(genre.getGenreNm());
            genreDtos.add(genreDto);
        }
        return genreDtos;
    }

    private ArrayList<StillCutDto> mapStillCuts(List<StillCut> stillCuts) {
        ArrayList<StillCutDto> stillCutDtos = new ArrayList<>();
        for (StillCut stillCut : stillCuts) {
            StillCutDto stillCutDto = new StillCutDto();
            stillCutDto.setStillCut_url(stillCut.getStillCut_url());
            stillCutDtos.add(stillCutDto);
        }
        return stillCutDtos;
    }
}
