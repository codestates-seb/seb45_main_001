package com.sundayCinema.sundayCinema.movie.entity.boxOffice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sundayCinema.sundayCinema.comment.Comment;
import com.sundayCinema.sundayCinema.movie.entity.movieInfo.Movie;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BoxOfficeMovie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long boxOfficeId;
    /*박스오피스 조회 일자*/
    @Column
    private String openDt;
    /*해당일자의 박스오피스 순위*/
    @Column(name = "movieRank")
    private String rank;
    /*영화의 대표코드*/
    @Column
    private String movieCd;
    @Column
    private String movieNm;// "영화명(국문)"
    @Column(nullable = false)
    private String audiAcc; // "누적 관객수"
    @OneToOne
    @JoinColumn(name = "MOVIE_ID")
    private Movie movie;
    public BoxOfficeMovie(BoxOfficeMovie boxOfficeMovie) {
        this.openDt = boxOfficeMovie.getOpenDt();
        this.rank = boxOfficeMovie.getRank();
        this.movieCd = boxOfficeMovie.getMovieCd();
        this.movieNm = boxOfficeMovie.getMovieNm();
        this.audiAcc = boxOfficeMovie.getAudiAcc();
        this.movie=boxOfficeMovie.getMovie();
    }
}
