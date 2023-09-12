package com.sundayCinema.sundayCinema.movie.api.TMDB;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VideoInfo {
    private String key;
    private String name;
    private String site;

    @JsonProperty("key")
    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getSite() {
        return site;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSite(String site) {
        this.site = site;
    }
}
