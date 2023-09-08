package com.sundayCinema.sundayCinema.movie.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
@Getter
@Setter
public class TrailerDto {

    private String vodClass;
    private String trailer_url;
}
