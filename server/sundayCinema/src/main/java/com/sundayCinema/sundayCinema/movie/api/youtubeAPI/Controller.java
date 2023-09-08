//package com.sundayCinema.sundayCinema.movie.api.youtubeAPI;
//
//import com.sundayCinema.sundayCinema.movie.api.youtubeAPI.YoutubeService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.IOException;
//import java.security.GeneralSecurityException;
//
//@RestController
//public class Controller {
//
//    @Autowired
//    private YoutubeService youtubeService;
//
//    @GetMapping("/review/{movieName}")
//    public String getReview(@PathVariable String movieName) {
//        try {
//            String videoId = youtubeService.getMovieReview(movieName);
//            if (videoId != null) {
//                return "https://www.youtube.com/watch?v=" + videoId;
//            } else {
//                return "No latest movie trailer found.";
//            }
//        } catch (GeneralSecurityException | IOException e) {
//            e.printStackTrace();
//            return "Error occurred while fetching data.";
//        }
//    }
//
//}
