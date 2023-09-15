package com.sundayCinema.sundayCinema.movie.entity.movieMedia;

import com.sundayCinema.sundayCinema.movie.entity.movieInfo.Movie;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class YoutubeReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long reviewId;
    @Column
    private String youtubeChannel;
    @Column
    private String youtubeReview_url;
    @Column
    private String thumbnail;

    @ManyToOne   //
    @JoinColumn(name = "MOVIE_ID")
    private Movie movie;
}
