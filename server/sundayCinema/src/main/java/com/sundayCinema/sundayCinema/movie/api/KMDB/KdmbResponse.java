package com.sundayCinema.sundayCinema.movie.api.KMDB;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class KdmbResponse {
    @JsonProperty("Data")
    private List<DataItem> Data;
    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DataItem {
        @JsonProperty("Result")
        private List<ResultItem> Result;

        @Getter
        @Setter
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class ResultItem {

            private String title;
            private String titleEng;
            private String titleEtc;
            private Plot plots;
            private String posters;
            private String stlls;
            private Vod vods;
        }
    }

    @Getter
    @Setter
    public static class Plot {
        private List<PlotInfo> plot;
        @Getter
        @Setter
        public static class PlotInfo {
            private String plotLang;
            private String plotText;
        }
    }
    @Getter
    @Setter
    public static class Vod {
        private List<VodInfo> vod;
        @Getter
        @Setter
        public static class VodInfo {
            private String vodClass;
            private String vodUrl;
        }
    }
}

