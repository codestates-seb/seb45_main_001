package com.sundayCinema.sundayCinema.movie.BoxOfficeMovie;

import com.sundayCinema.sundayCinema.comment.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class BoxOfficeMovie {
    @Id
    private long movieId;
    /*박스오피스 조회 일자*/
    @Column
    private String showRange;
    /*해당일자의 박스오피스 순위*/
    @Column
    private String rank;
    /*영화의 대표코드*/
    @Column
    private String movieCd;
    @Column
    private String movieNm;// "영화명(국문)"


    /////////////////////////////////////////////////////////////
    @Column
    private String showTm;// "상영시간"
    @Column
    private String nationNm; // "제작 국가명"
//    @Column
//    private String peopleNm; // ["배우명" 0~3번까지]
    @Column
    private String watchGradeNm; // "관람 등급 명칭"
    @Column
    private String repGenreNm; // : "대표 장르명",
    @Column
    private String openDt; // "개봉일",
    @Column
    private String audiAcc; // "누적 관객수",
    @Column
    private String poster_image_url;
    @Column
    private String trailer_url;
    @Column
    private String region; //: "해외"


//    @Column
//    private List<Comment> comment_list;// : "댓글 리스트"
//    @Column
//    private double avg_score; // : "평균 평점"
}
