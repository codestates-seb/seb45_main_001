package com.sundayCinema.sundayCinema.movie.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MovieDto {

    @Getter
    @Setter
    public static class MovieResponseDto {
        private long movieId;
        private String movieCd;
        private String movieNm;
        private String movieNmEn;
        private String movieNmOg;
        private String showTm;
        private String prdtYear;
        private String openDt;
        private String prdtStatNm;
        private String typeNm;
        private Double averageScore; // 추가된 필드: 평균평점

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime createdAt;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime modifiedAt;
    }
}
