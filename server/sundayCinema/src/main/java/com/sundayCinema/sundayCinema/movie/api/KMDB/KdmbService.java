package com.sundayCinema.sundayCinema.movie.api.KMDB;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sundayCinema.sundayCinema.movie.api.youtubeAPI.YoutubeService;
import com.sundayCinema.sundayCinema.movie.entity.boxOffice.BoxOfficeMovie;
import com.sundayCinema.sundayCinema.movie.entity.movieMedia.Plot;
import com.sundayCinema.sundayCinema.movie.entity.movieMedia.Poster;
import com.sundayCinema.sundayCinema.movie.entity.movieMedia.StillCut;
import com.sundayCinema.sundayCinema.movie.repository.movieInfoRepo.MovieRepository;
import com.sundayCinema.sundayCinema.movie.repository.movieMediaRepo.PlotRepository;
import com.sundayCinema.sundayCinema.movie.repository.movieMediaRepo.PosterRepository;
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
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class KdmbService {
    @Value("${KDMB_API_KEY}")
    private String KdmbApiKey; // 서비스키를 직접 입력
    private String kdmbUrl = "http://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp";


    public KdmbResponse generateKdmb(BoxOfficeMovie boxOfficeMovie) throws IOException {
            String openDt = boxOfficeMovie.getOpenDt();
            String movieNm = boxOfficeMovie.getMovieNm();
            String apiUrl = buildApiUrl(openDt, movieNm);

            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            conn.setRequestProperty("ServiceKey", KdmbApiKey); // 인증키 추가

            String apiResponse = readApiResponse(conn);
            conn.disconnect();
            KdmbResponse kdmbResponse = parsingKdmb(apiResponse);
            return kdmbResponse;
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