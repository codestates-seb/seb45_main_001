package com.sundayCinema.sundayCinema.movie.dto.detaiPagelDto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DetailsMediaInfo{
    public List<StillCutDto> stillCuts;
    public String trailers;
    public String youtubeReviews;
}
