package com.sundayCinema.sundayCinema.movie.entity.movieMedia;

import com.sundayCinema.sundayCinema.movie.entity.movieInfo.Movie;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Trailer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long trailerId;
    @Column
    private String vodClass;
    @Column
    private String trailer_url;
    @ManyToOne   //
    @JoinColumn(name = "MOVIE_ID")
    private Movie movie;
}
