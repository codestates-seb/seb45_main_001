package com.sundayCinema.sundayCinema.movie.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DetailsMediaInfo{
    public List<String> stillCuts;
    public String trailers;
    public List<String> youtubeReviews;
}
