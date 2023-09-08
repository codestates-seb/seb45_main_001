package com.sundayCinema.sundayCinema.movie.entity;

import com.sundayCinema.sundayCinema.movie.entity.Movie;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Poster {
    @Id
    private long posterId;
    @Column
    private String poster_image_url;
    @ManyToOne   //
    @JoinColumn(name = "MOVIE_ID")
    private Movie movie;
}
