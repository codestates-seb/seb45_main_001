package com.sundayCinema.sundayCinema.movie.api.youtubeAPI;

import com.sundayCinema.sundayCinema.movie.api.KMDB.KdmbService;
import com.sundayCinema.sundayCinema.movie.api.youtubeAPI.YoutubeService;
import com.sundayCinema.sundayCinema.movie.entity.YoutubeReview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@RestController
public class Controller {

    @Autowired
    private YoutubeService youtubeService;
    @Autowired
    private KdmbService kdmbService;
    @GetMapping("/review/{movieName}")
    public void getReview(@PathVariable String movieName) throws GeneralSecurityException, IOException {

        youtubeService.getMovieReview(movieName);
    }

}
