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
public class Plots {
    @Id
    private long plotId;

    @Column(columnDefinition = "text")
    private String plotText;
    @ManyToOne   //
    @JoinColumn(name = "MOVIE_ID")
    private Movie movie;
}
