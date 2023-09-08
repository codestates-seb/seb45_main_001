package com.sundayCinema.sundayCinema.movie.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DetailsMainInfo{
    public String plot;
    public List<ActorDto> actors; // 영화 배우 이름(국문)
    public String director;
}