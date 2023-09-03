//package com.sundayCinema.sundayCinema.movie.Service;
//
//import com.sundayCinema.sundayCinema.movie.api.KMDB.KdmbResponse;
//import com.sundayCinema.sundayCinema.movie.api.KMDB.KdmbService;
//import com.sundayCinema.sundayCinema.movie.api.KOBIS.KobisService;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//import org.springframework.web.bind.annotation.GetMapping;
//
//@Service
//public class MovieService {
//    private final KobisService kobisService;
//    private final KdmbService kdmbService;
//    private final KdmbResponse kdmbResponse;
//
//    public MovieService(KobisService kobisService, KdmbService kdmbService, KdmbResponse kdmbResponse) {
//        this.kobisService = kobisService;
//        this.kdmbService = kdmbService;
//        this.kdmbResponse = kdmbResponse;
//    }
//
//   @Scheduled(cron = "0 0 0 * * ?")
//   public void fetchMovieData() {
//
//  }
//}