package com.sundayCinema.sundayCinema.movie.Service;

import com.sundayCinema.sundayCinema.movie.dto.mainPageDto.BoxOfficeMovieDto;
import com.sundayCinema.sundayCinema.movie.entity.boxOffice.BoxOfficeMovie;
import com.sundayCinema.sundayCinema.movie.entity.boxOffice.ForeignBoxOffice;
import com.sundayCinema.sundayCinema.movie.entity.boxOffice.GenreBoxOffice;
import com.sundayCinema.sundayCinema.movie.entity.boxOffice.KoreaBoxOffice;
import com.sundayCinema.sundayCinema.movie.mapper.BoxOfficeMovieMapper;
import com.sundayCinema.sundayCinema.movie.repository.boxOfficeRepo.BoxOfficeMovieRepository;
import com.sundayCinema.sundayCinema.movie.repository.boxOfficeRepo.ForeignBoxOfficeRepository;
import com.sundayCinema.sundayCinema.movie.repository.boxOfficeRepo.GenreBoxOfficeRepository;
import com.sundayCinema.sundayCinema.movie.repository.boxOfficeRepo.KoreaBoxOfficeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class BoxOfficeSwitchService {
    private final BoxOfficeMovieRepository boxOfficeMovieRepository;
    private final KoreaBoxOfficeRepository koreaBoxOfficeRepository;
    private final ForeignBoxOfficeRepository foreignBoxOfficeRepository;
    private final GenreBoxOfficeRepository genreBoxOfficeRepository;

    private final BoxOfficeMovieMapper boxOfficeMovieMapper;

    public BoxOfficeSwitchService(BoxOfficeMovieRepository boxOfficeMovieRepository, KoreaBoxOfficeRepository koreaBoxOfficeRepository, ForeignBoxOfficeRepository foreignBoxOfficeRepository,
                                  GenreBoxOfficeRepository genreBoxOfficeRepository, BoxOfficeMovieMapper boxOfficeMovieMapper) {
        this.boxOfficeMovieRepository = boxOfficeMovieRepository;
        this.koreaBoxOfficeRepository = koreaBoxOfficeRepository;
        this.foreignBoxOfficeRepository = foreignBoxOfficeRepository;
        this.genreBoxOfficeRepository = genreBoxOfficeRepository;
        this.boxOfficeMovieMapper = boxOfficeMovieMapper;
    }

    //박스 오피스 타입을 변환해서 저장
    public void saveBoxOfficeByNationCd(String repNationCd, BoxOfficeMovie boxOfficeMovie){
        if(repNationCd.equals("")){
            BoxOfficeMovie newBoxOfficeMovie = new BoxOfficeMovie(boxOfficeMovie);
            boxOfficeMovieRepository.save(newBoxOfficeMovie);
        }
        else if(repNationCd.equals("K")){
            koreaBoxOfficeRepository.save(createKoreaBoxOffice(boxOfficeMovie));
        }
        else if(repNationCd.equals("F")){
            foreignBoxOfficeRepository.save(createForeignBoxOffice(boxOfficeMovie));
        }
        else if(repNationCd.equals("G")){
            genreBoxOfficeRepository.save(createGenreBoxOffice(boxOfficeMovie));
        }

    }
    public List<BoxOfficeMovieDto> loadBoxOfficeByBoxNm(String boxOfficeCd) {
        List<BoxOfficeMovieDto> boxOfficeMovieDtos = new ArrayList<>();

        if ("".equals(boxOfficeCd)) {
            List<BoxOfficeMovie> boxOfficeMovies = boxOfficeMovieRepository.findAll();
            for (BoxOfficeMovie box : boxOfficeMovies) {
                BoxOfficeMovieDto boxOfficeMovieDto = boxOfficeMovieMapper.boxOfficeResponseDto(box);
                boxOfficeMovieDtos.add(boxOfficeMovieDto);
            }
        } else if ("K".equals(boxOfficeCd)) {
            List<KoreaBoxOffice> koreaBoxOffices = koreaBoxOfficeRepository.findAll();
            for (KoreaBoxOffice koreaBox : koreaBoxOffices) {
                BoxOfficeMovieDto boxOfficeMovieDto = boxOfficeMovieMapper.boxOfficeResponseDto(koreaBox);
                boxOfficeMovieDtos.add(boxOfficeMovieDto);
            }
        } else if ("F".equals(boxOfficeCd)) {
            List<ForeignBoxOffice> foreignBoxOffices = foreignBoxOfficeRepository.findAll();
            for (ForeignBoxOffice foreignBox : foreignBoxOffices) {
                BoxOfficeMovieDto boxOfficeMovieDto = boxOfficeMovieMapper.boxOfficeResponseDto(foreignBox);
                boxOfficeMovieDtos.add(boxOfficeMovieDto);
            }
        }

        return boxOfficeMovieDtos;
    }
    private KoreaBoxOffice createKoreaBoxOffice(BoxOfficeMovie boxOfficeMovie) {
        KoreaBoxOffice koreaBoxOffice = new KoreaBoxOffice(boxOfficeMovie);

        return koreaBoxOffice;
    }

    private ForeignBoxOffice createForeignBoxOffice(BoxOfficeMovie boxOfficeMovie) {
        ForeignBoxOffice foreignBoxOffice = new ForeignBoxOffice(boxOfficeMovie);

        return foreignBoxOffice;
    }
    private GenreBoxOffice createGenreBoxOffice(BoxOfficeMovie boxOfficeMovie) {
        GenreBoxOffice genreBoxOffice = new GenreBoxOffice(boxOfficeMovie);
        return genreBoxOffice;
    }
}
