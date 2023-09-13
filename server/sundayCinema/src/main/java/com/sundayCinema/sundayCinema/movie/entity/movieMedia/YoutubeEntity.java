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
public class YoutubeEntity {
    @Id
    private long youtubeId;
    @Column
    private String youtubeChannel;
    @Column
    private String youtubeVod_url;
    @Column
    private String thumbnail;

    @ManyToOne   //
    @JoinColumn(name = "MOVIE_ID")
    private Movie movie;
}
