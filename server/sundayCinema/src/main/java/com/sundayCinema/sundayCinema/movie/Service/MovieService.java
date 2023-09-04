package com.sundayCinema.sundayCinema.movie.Service;

import com.sundayCinema.sundayCinema.movie.entity.BoxOfficeMovie;
import com.sundayCinema.sundayCinema.movie.entity.Movie;
import com.sundayCinema.sundayCinema.movie.entity.Poster;
import com.sundayCinema.sundayCinema.movie.entity.Trailer;
import com.sundayCinema.sundayCinema.movie.repository.BoxOfficeMovieRepository;
import com.sundayCinema.sundayCinema.movie.repository.MovieRepository;
import com.sundayCinema.sundayCinema.movie.repository.PosterRepository;
import com.sundayCinema.sundayCinema.movie.repository.TrailerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    private final TrailerRepository trailerRepository;
    private final MovieRepository movieRepository;
    private final PosterRepository posterRepository;

    private final BoxOfficeMovieRepository boxOfficeMovieRepository;

    public MovieService(TrailerRepository trailerRepository, MovieRepository movieRepository, PosterRepository posterRepository,
                        BoxOfficeMovieRepository boxOfficeMovieRepository) {
        this.trailerRepository = trailerRepository;
        this.movieRepository = movieRepository;
        this.posterRepository = posterRepository;
        this.boxOfficeMovieRepository = boxOfficeMovieRepository;
    }

    public List<Trailer> loadTrailerList(Movie movie){
        List<Trailer> trailerList= trailerRepository.findByMovie(movie);
        return trailerList;
    }

  public Trailer loadTrailer(long movieId, String keyword){

        Trailer mainTrailer = trailerRepository.findByMovie_MovieIdAndVodClassContaining(movieId,keyword);

        return mainTrailer;
  }

  public Movie loadMovie(String movieNm){
        Movie findMovie = movieRepository.findByMovieNm(movieNm);

        return findMovie;
  }
    public List<Poster> loadPosterList(Movie movie){
        List<Poster> posterList= posterRepository.findByMovie(movie);
        return posterList;
    }

  public List<BoxOfficeMovie> loadBoxOffice(){

        return boxOfficeMovieRepository.findAll();
  }


}