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
public class Poster {
    @Id
    private String posterId;
    @Column
    private String poster_image_url;

}
