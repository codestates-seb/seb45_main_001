package com.sundayCinema.sundayCinema.movie.api.apiResponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sundayCinema.sundayCinema.movie.entity.boxOffice.BoxOfficeMovie;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class BoxofficeResponse {

    @JsonProperty("boxOfficeResult")
    private BoxofficeData boxOfficeResult;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    @Setter
    public static class BoxofficeData {
        @JsonProperty("boxofficeType")
        private String boxofficeType;
        @JsonProperty("showRange")
        private String showRange;
        @JsonProperty("dailyBoxOfficeList")
        private List<BoxOfficeMovie> dailyBoxOfficeList;
    }
}
