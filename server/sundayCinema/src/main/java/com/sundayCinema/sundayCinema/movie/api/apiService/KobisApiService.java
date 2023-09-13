package com.sundayCinema.sundayCinema.movie.api.apiService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.sundayCinema.sundayCinema.movie.api.apiResponse.BoxofficeResponse;
import com.sundayCinema.sundayCinema.movie.entity.boxOffice.BoxOfficeMovie;
import kr.or.kobis.kobisopenapi.consumer.rest.KobisOpenAPIRestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
public class KobisApiService {
    @Value("${KOBIS_API_KEY}")
    private String KobisApiKEY;

    String boxResponse = "";
    String movieResponse = "";
    //전날 박스오피스 조회 ( 오늘 날짜꺼는 안나옴.. )
    LocalDateTime time = LocalDateTime.now().minusDays(1);
    String targetDt = time.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

    //ROW 개수
    String itemPerPage = "10";

    //다양성영화(Y)/상업영화(N)/전체(default)
    String multiMovieYn = "";

    //한국영화(K)/외국영화(F)/전체(default)
    String repNationCd = "";
    String repNationCd_Korean = "K";
    String repNationCd_Foreign = "F";

    //상영지역별 코드/전체(default)
    String wideAreaCd = "";

    String[] movieTypeCdArr = new String[0];


    public List<BoxOfficeMovie> searchingDailyBoxOffice(String repNationCd) throws Exception {
        KobisOpenAPIRestService service = new KobisOpenAPIRestService(KobisApiKEY);

        // 일일 박스오피스 서비스 호출 (boolean isJson, String targetDt, String itemPerPage,String multiMovieYn, String repNationCd, String wideAreaCd)
        boxResponse = service.getDailyBoxOffice(true, targetDt, itemPerPage, multiMovieYn, repNationCd, wideAreaCd);

        BoxofficeResponse parsingResponse = parsingKobis(boxResponse);
        List<BoxOfficeMovie> dailyList = parsingResponse.getBoxOfficeResult().getDailyBoxOfficeList();

        return dailyList;
    }

    public List<BoxOfficeMovie> searchingBoxOffice(String targetDt) throws Exception {
        KobisOpenAPIRestService service = new KobisOpenAPIRestService(KobisApiKEY);

        // 일일 박스오피스 서비스 호출 (boolean isJson, String targetDt, String itemPerPage,String multiMovieYn, String repNationCd, String wideAreaCd)
        boxResponse = service.getDailyBoxOffice(true, targetDt, itemPerPage, multiMovieYn, repNationCd, wideAreaCd);

        BoxofficeResponse parsingResponse = parsingKobis(boxResponse);
        List<BoxOfficeMovie> dailyList = parsingResponse.getBoxOfficeResult().getDailyBoxOfficeList();

        return dailyList;
    }

    public BoxofficeResponse parsingKobis(String response) throws JsonProcessingException {
        String jsonString = response;

        ObjectMapper objectMapper = new ObjectMapper();
        BoxofficeResponse movieData = objectMapper.readValue(jsonString, BoxofficeResponse.class);

        return movieData;
    }

}
