package com.sundayCinema.sundayCinema.movie.entity.movieInfo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Actor {
    @Id
    private long actorId;
    @Column
    private String peopleNm;
    @Column
    private String peopleNmEn;
    @Column(name = "cast")
    private String cast;
    @Column
    private String castEn;
    @ManyToOne   //
    @JoinColumn(name = "MOVIE_ID")
    private Movie movie;
}
