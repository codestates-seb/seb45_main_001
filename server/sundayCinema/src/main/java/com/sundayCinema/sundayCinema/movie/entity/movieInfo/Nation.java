package com.sundayCinema.sundayCinema.movie.entity.movieInfo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Nation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long nationId;
    @Column
    private String nationNm;

    @ManyToOne   //
    @JoinColumn(name = "MOVIE_ID")
    private Movie movie;
}
