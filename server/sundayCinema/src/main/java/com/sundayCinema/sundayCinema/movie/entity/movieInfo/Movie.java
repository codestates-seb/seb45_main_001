package com.sundayCinema.sundayCinema.movie.entity.movieInfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sundayCinema.sundayCinema.advice.audit.Auditable;
import com.sundayCinema.sundayCinema.comment.Comment;
import com.sundayCinema.sundayCinema.movie.entity.movieMedia.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long movieId;
    @Column(nullable = false)
    private String movieCd;
    @Column(nullable = false)
    private String movieNm;
    @Column(nullable = false)
    private String movieNmEn;
    @Column(nullable = false)
    private String movieNmOg;
    @Column(nullable = false)
    private String showTm;// "상영시간"
    @Column(nullable = false)
    private String prdtYear;
    @Column(nullable = false)
    private String openDt;
    @Column
    private String poster;
    @Column(nullable = false)
    private String prdtStatNm;
    @Column(nullable = false)
    private String typeNm;
    @Column
    private String backDrop;
    @Column(columnDefinition = "text")
    private String plot;


    @OneToMany(mappedBy = "movie")
    private List<MovieAudit> audits; // "관람 등급 명칭"
    @OneToMany(mappedBy = "movie")
    private List<Nation> nations = new ArrayList<>();
    @OneToMany(mappedBy = "movie")
    private List<Actor> actors = new ArrayList<>();
    @OneToMany(mappedBy = "movie")
    private List<Genre> genres = new ArrayList<>();
    @OneToMany(mappedBy = "movie")
    private List<Director> directors = new ArrayList<>();
    @OneToMany(mappedBy = "movie")
    private List<YoutubeEntity> youtubeEntities = new ArrayList<>();
    @OneToMany(mappedBy = "movie")
    private List<StillCut> stillCuts = new ArrayList<>();
    @OneToMany(mappedBy = "movie")
    private List<Trailer> trailers = new ArrayList<>();
    @OneToMany(mappedBy = "movie")
    private List<Comment> comments = new ArrayList<>();
}
