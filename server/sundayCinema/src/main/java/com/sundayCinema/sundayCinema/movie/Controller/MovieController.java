//package com.sundayCinema.sundayCinema.movie.Controller;
//
//import com.sundayCinema.sundayCinema.movie.api.KOBIS.OpenApiService;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping
//public class MovieController {
//
//    private final OpenApiService openApiService;
//
//    public MovieController(OpenApiService openApiService) {
//        this.openApiService = openApiService;
//    }
//
//    /*
//             1.GET 홈화면(메인페이지)에서 Top 10 박스오피스 출력
//                  "id" : 1,
//                    "movieNm" : "오펜하이머",
//                     "poster_image_url" : "~~~~~",
//                      "trailer_url" : "~~~~~~",
//                         "rank" : "1",
//                         "region" : "해외"
//
//         */
//    @GetMapping("/")
//    public void test(){
//        openApiService.dailyBoxOffice();
//    }
//}
