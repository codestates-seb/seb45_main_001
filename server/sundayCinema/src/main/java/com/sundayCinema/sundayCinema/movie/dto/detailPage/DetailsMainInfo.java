package com.sundayCinema.sundayCinema.movie.dto.detailPage;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DetailsMainInfo{
    public String plot;
    public List<ActorDto> actors;
    public String director;
}