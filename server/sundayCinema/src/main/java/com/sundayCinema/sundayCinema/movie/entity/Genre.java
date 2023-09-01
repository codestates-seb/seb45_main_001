package com.sundayCinema.sundayCinema.movie.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Genre {
    @Id
    private String genreId;
    @Column
    private String repGenreNm; // : "대표 장르명",
}
