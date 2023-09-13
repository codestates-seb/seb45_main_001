package com.sundayCinema.sundayCinema.movie.api.apiService;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sundayCinema.sundayCinema.movie.api.apiRepositoryService.KdmbRepositoryService;
import com.sundayCinema.sundayCinema.movie.api.apiResponse.KdmbResponse;

import com.sundayCinema.sundayCinema.movie.entity.movieInfo.Movie;
import com.sundayCinema.sundayCinema.movie.repository.movieInfoRepo.MovieRepository;

import com.sundayCinema.sundayCinema.movie.repository.movieMediaRepo.StillCutRepository;
import com.sundayCinema.sundayCinema.movie.repository.movieMediaRepo.TrailerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;


@Service
@Slf4j
public class KdmbApiService {
    @Value("${KDMB_API_KEY}")
    private String KdmbApiKey; // 서비스키를 직접 입력
    private String kdmbUrl = "http://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp";

    private final MovieRepository movieRepository;
    private final KdmbRepositoryService kdmbRepositoryService;

    private final StillCutRepository stillCutRepository;
    private final TrailerRepository trailerRepository;

    public KdmbApiService(MovieRepository movieRepository, KdmbRepositoryService kdmbRepositoryService,
                          StillCutRepository stillCutRepository, TrailerRepository trailerRepository) {
        this.movieRepository = movieRepository;
        this.kdmbRepositoryService = kdmbRepositoryService;
        this.stillCutRepository = stillCutRepository;
        this.trailerRepository = trailerRepository;
    }

    public void generateKdmb(String movieNm, String openDt) throws IOException, GeneralSecurityException {
        Movie findMovie= movieRepository.findByMovieNm(movieNm);
        log.info("영화 :" + findMovie.getMovieNm());
        if (stillCutRepository.existsById(findMovie.getMovieId())||trailerRepository.existsById(findMovie.getMovieId())) {
            // true일 때 아무 작업도 수행하지 않음
        } else {
            log.info("통과한 영화 :" + findMovie.getMovieNm());
            String apiUrl = buildApiUrl(openDt, movieNm);
            URL url = new URL(apiUrl);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            conn.setRequestProperty("ServiceKey", KdmbApiKey); // 인증키 추가

            System.out.println("kdmb Response code: " + conn.getResponseCode());

            String apiResponse = readApiResponse(conn);
            conn.disconnect();
            KdmbResponse kdmbResponse = parsingKdmb(apiResponse);
            log.info("KdmbResponse :" +kdmbResponse.toString());
            kdmbRepositoryService.saveKdmb(kdmbResponse, findMovie);
        }
    }


    public KdmbResponse parsingKdmb(String response) throws JsonProcessingException {
        String jsonString = response;

        ObjectMapper objectMapper = new ObjectMapper();
        KdmbResponse movieData = objectMapper.readValue(jsonString, KdmbResponse.class);

        return movieData;
    }


    private String readApiResponse(HttpURLConnection conn) throws IOException {
        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();

        return sb.toString();
    }

    private String buildApiUrl(String openDt, String title) throws UnsupportedEncodingException {
        UrlParameterBuilder urlParameterBuilder = new UrlParameterBuilder();
        // URL 파라미터 추가
        urlParameterBuilder.addParameter("ServiceKey", KdmbApiKey);
        urlParameterBuilder.addParameter("collection", "kmdb_new2");
        if (openDt != null && !openDt.isEmpty()) {
            urlParameterBuilder.addParameter("releaseDts", openDt);
        }
        urlParameterBuilder.addParameter("title", title);

        return kdmbUrl + urlParameterBuilder.build();
    }

    public class UrlParameterBuilder {
        private Map<String, String> parameters = new HashMap<>();

        public void addParameter(String key, String value) {
            parameters.put(key, value);
        }

        public String build() throws UnsupportedEncodingException {
            StringBuilder urlBuilder = new StringBuilder();
            boolean isFirstParameter = true;

            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                if (isFirstParameter) {
                    urlBuilder.append("?");
                    isFirstParameter = false;
                } else {
                    urlBuilder.append("&");
                }
                urlBuilder.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                urlBuilder.append("=");
                urlBuilder.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            }
            return urlBuilder.toString();
        }
    }
}