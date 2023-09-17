package com.sundayCinema.sundayCinema.movie.entity.movieInfo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "DIRECTOR")
public class Director {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long directorId;
    @Column
    private String peopleNm;
    @Column
    private String peopleNmEn;
    @ManyToOne   //
    @JoinColumn(name = "MOVIE_ID")
    private Movie movie;
}
