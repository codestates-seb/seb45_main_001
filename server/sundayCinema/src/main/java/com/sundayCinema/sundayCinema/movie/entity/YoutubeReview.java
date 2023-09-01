package com.sundayCinema.sundayCinema.movie.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class YoutubeReview {
    @Id
    private String reviewId;
    @Column
    private String youtubeReview_url;
}
