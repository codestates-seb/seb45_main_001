package com.sundayCinema.sundayCinema.movie.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class StillCut {
    @Id
    private long  stillCutId;
    @Column
    private String stillCut_url;
    @ManyToOne   //
    @JoinColumn(name = "MOVIE_ID")
    private Movie movie;
}
