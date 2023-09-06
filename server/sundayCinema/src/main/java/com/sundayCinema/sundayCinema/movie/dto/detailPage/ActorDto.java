package com.sundayCinema.sundayCinema.movie.dto.detailPage;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
@Getter
@Setter
public class ActorDto {
    private String peopleNm;
    private String peopleNmEn;
    private String cast;
    private String castEn;
}
