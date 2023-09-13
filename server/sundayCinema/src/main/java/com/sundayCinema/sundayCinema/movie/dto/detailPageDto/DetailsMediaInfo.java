package com.sundayCinema.sundayCinema.movie.dto.detailPageDto;

import com.sundayCinema.sundayCinema.movie.dto.mainPageDto.TrailerDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DetailsMediaInfo{
    public List<String> stillCuts;
    public List<TrailerDto> trailers;
    public List<String> youtubeReviews;
}
