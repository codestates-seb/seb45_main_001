package com.sundayCinema.sundayCinema.movie.Controller;

import com.sundayCinema.sundayCinema.movie.api.KMDB.KdmbService;
import com.sundayCinema.sundayCinema.movie.api.KOBIS.KobisService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class MovieController {

    private final KobisService openApiService;
    private final KdmbService kdmbService;

    public MovieController(KobisService openApiService, KdmbService kdmbService) {
        this.openApiService = openApiService;
        this.kdmbService = kdmbService;
    }

    @GetMapping("/test")
    public void test() throws Exception {
        openApiService.dailyBoxOffice();
    }
    @GetMapping("/test2")
    public void test2() throws Exception {
        kdmbService.generateKdmb("오펜하이머");
    }
}
