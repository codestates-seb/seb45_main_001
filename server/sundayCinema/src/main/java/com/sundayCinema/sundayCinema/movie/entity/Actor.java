package com.sundayCinema.sundayCinema.movie.entity;

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
    private String actorId;
    @Column
    private String actorName;
    @ManyToOne   //
    @JoinColumn(name = "MOVIE_ID")
    private Movie movie;
}
