package com.sundayCinema.sundayCinema.movie.entity;

import com.sundayCinema.sundayCinema.advice.audit.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Movie extends Auditable {
    @Id
    private long movieId;
    @Column(nullable = false)
    private String movieNm;// "영화명(국문)"
    @Column(nullable = false)
    private String movieCd;
    @Column(nullable = false)
    private String showTm;// "상영시간"
    @Column(nullable = false)
    private String nationNm; // "제작 국가명"
    @Column(nullable = false)
    private String watchGradeNm; // "관람 등급 명칭"
    @Column(nullable = false)
    private String openDt; // "개봉일"
    @Column
    private String region; //: "해외"
    @OneToMany(mappedBy = "movie")
    private List<Actor> actors = new ArrayList<>();
}
