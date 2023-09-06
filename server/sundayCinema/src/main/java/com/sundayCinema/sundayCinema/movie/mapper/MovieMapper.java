package com.sundayCinema.sundayCinema.movie.mapper;

import com.sundayCinema.sundayCinema.movie.dto.MovieDto;
import com.sundayCinema.sundayCinema.movie.entity.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    @Mappings({
            @Mapping(source = "movieId", target = "movieId"),
            @Mapping(source = "movieCd", target = "movieCd"),
            @Mapping(source = "movieNm", target = "movieNm"),
            @Mapping(source = "movieNmEn", target = "movieNmEn"),
            @Mapping(source = "movieNmOg", target = "movieNmOg"),
            @Mapping(source = "showTm", target = "showTm"),
            @Mapping(source = "prdtYear", target = "prdtYear"),
            @Mapping(source = "openDt", target = "openDt"),
            @Mapping(source = "prdtStatNm", target = "prdtStatNm"),
            @Mapping(source = "typeNm", target = "typeNm"),
            @Mapping(source = "averageScore", target = "averageScore"),
            @Mapping(source = "createdAt", target = "createdAt"),
            @Mapping(source = "modifiedAt", target = "modifiedAt")
    })
    MovieDto.MovieResponseDto toDto(Movie movie);

    // 다른 매핑 메서드 추가 가능
}
