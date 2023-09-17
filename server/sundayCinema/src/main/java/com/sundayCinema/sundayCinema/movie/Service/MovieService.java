package com.sundayCinema.sundayCinema.movie.Service;

import com.sundayCinema.sundayCinema.movie.api.ApiRepoService.KobisRepoService;
import com.sundayCinema.sundayCinema.movie.api.ApiRepoService.MediaRepoService;
import com.sundayCinema.sundayCinema.movie.dto.mainPageDto.GenreMovieDto;
import com.sundayCinema.sundayCinema.movie.entity.boxOffice.BoxOfficeMovie;
import com.sundayCinema.sundayCinema.movie.entity.movieInfo.Movie;
import com.sundayCinema.sundayCinema.movie.mapper.GenreMovieMapper;
import com.sundayCinema.sundayCinema.movie.repository.movieInfoRepo.MovieRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class MovieService {

    private final MovieRepository movieRepository;
    private final GenreMovieMapper genreMovieMapper;
    private final KobisRepoService kobisRepoService;
    private final MediaRepoService mediaRepoService;

    public MovieService(MovieRepository movieRepository, GenreMovieMapper genreMovieMapper,
                        KobisRepoService kobisRepoService, MediaRepoService mediaRepoService) {
        this.movieRepository = movieRepository;
        this.genreMovieMapper = genreMovieMapper;
        this.kobisRepoService = kobisRepoService;
        this.mediaRepoService = mediaRepoService;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void dailyUpdateAll() throws Exception {
        saveTop10All();
    }

    public void saveGenreAll(String date) throws Exception {
        List<BoxOfficeMovie> genreList= kobisRepoService.saveGenreBox(date);
        saveMedia(genreList);
    }
    public void saveTop10All() throws Exception {
        List<BoxOfficeMovie> top10Box= kobisRepoService.searchAndSaveBoxOfficeByNationCd("");
        saveMedia(top10Box);
        List<BoxOfficeMovie> kBox= kobisRepoService.searchAndSaveBoxOfficeByNationCd("K");
        saveMedia(kBox);
        List<BoxOfficeMovie> fBox= kobisRepoService.searchAndSaveBoxOfficeByNationCd("F");
        saveMedia(fBox);
    }

    // 액션, 코메디, 드라마, 애니메이션, 스릴러, 판타지, 멜로/로맨스, 공포(호러), 어드밴처, 범죄
    public List<GenreMovieDto>loadGenreMovie(String nation){
        String[] genreArray={"액션", "코메디", "드라마", "애니메이션", "스릴러", "판타지", "멜로/로맨스", "공포(호러)", "어드밴처", "범죄"};
        List<GenreMovieDto>genreMovieDtos=new ArrayList<>();
        List<Movie> movies = new ArrayList<>();

        if(nation.equals("종합"))movies= movieRepository.findAll();
        else if (nation.equals("국내"))movies= movieRepository.findByNationsNationNm("한국");
        else if(nation.equals("해외")) movies=movieRepository.findByNationsNationNmIsNot("한국");

        for (Movie findMovie : movies) {
           for(String genreNm : genreArray)
            if(parsingGenre(findMovie,genreNm)){
                GenreMovieDto genreMovieDto= genreMovieMapper.responseGenreMovieDto(findMovie,genreNm);
                genreMovieDtos.add(genreMovieDto);
            }
        }
        return genreMovieDtos;
    }

    public void saveMedia(List<BoxOfficeMovie> boxList) throws IOException, GeneralSecurityException {
        mediaRepoService.saveBackDrop(boxList);
        mediaRepoService.savePoster(boxList);
        mediaRepoService.savePlot(boxList);
        mediaRepoService.saveTrailer(boxList);
        mediaRepoService.saveStill(boxList);
        mediaRepoService.saveYoutubeReview(boxList);
    }
    public boolean parsingGenre(Movie movie, String genreNm) {
        for (int i = 0; i < movie.getGenres().size(); i++) {
            if (movie.getGenres().get(i).getGenreNm().equals(genreNm)) {
                return true;
            }
        }
        return false;
    }
}