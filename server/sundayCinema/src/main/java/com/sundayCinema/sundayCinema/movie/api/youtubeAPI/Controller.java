package com.sundayCinema.sundayCinema.movie.api.youtubeAPI;

import com.sundayCinema.sundayCinema.movie.api.KMDB.KdmbService;
import com.sundayCinema.sundayCinema.movie.api.youtubeAPI.YoutubeService;
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
    public String getReview(@PathVariable String movieName) {
        try {
            List<String> videoId = youtubeService.getMovieReview(movieName);
            String result="";
            if (videoId != null) {
                for (int i = 0; i < videoId.size(); i++) {

                    result+= "https://www.youtube.com/watch?v=" + videoId.get(i)+", ";
                }
                return result;
            } else {
                return "No latest movie trailer found.";
            }
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
            return "Error occurred while fetching data.";
        }
    }

}
